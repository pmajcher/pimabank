package tk.pimabank

import groovy.util.slurpersupport.NodeChild
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.support.RequestContextUtils
import java.text.NumberFormat


class OrderPeperoneController {

    def springSecurityService

    def index() {
         [	
			principal: springSecurityService.principal,
			order: MealOrder.getCurrent()]
    }


    def orderMealManually(){
		
		def locale = RequestContextUtils.getLocale(request)
		printf "locale: "+locale
		printf "params: "+params
		
		def mealOrder = MealOrder.getCurrent()
		if(mealOrder == null || !mealOrder.state == MealOrderState.OPEN){
			render template: 'meals', model: [principal: springSecurityService.principal,
				order: MealOrder.getCurrent()]
			return	
		}
		def price = params.price.replace(",", ".")
		printf "price: "+price
		
		def double d = Double.parseDouble(price)
		printf "d: "+d
		
		def int partOfMeal = 8
		printf "partOfMeal 1: "+params.partOfMeal
		if(params.isShared){
			partOfMeal = Integer.parseInt(params.partOfMeal)
		}
		printf "partOfMeal 2: "+partOfMeal
		if(partOfMeal < 0 || partOfMeal > 8 ){
			printf "return  1"
			return;
		}

		
		def agregator
		if(params.agregatorId){
			printf "connect meal with existing agregator"
			agregator = MealAgregator.get(params.agregatorId);
		}else{
			printf "connect meal with new agregator"
			agregator = new MealAgregator(mealPrice: d, 
										  mealName: params.what);
			agregator.save(flush: true)
		}
		

		printf "agregator: "+agregator.id
		
        def meal = new Meal(
                        user: User.get(getLoggedUserId()),
                        date: new Date(),
						approved: true,
						agregator: agregator,
						partOfMeal: partOfMeal);

        def user = User.get(getLoggedUserId())
        user.addToOperations(meal)
        if(!user.save(flush: true) ){
			user.errors.allErrors.each {
		        println "errors: " + it
		    }
		}

		mealOrder.addToMealAgregators(agregator)
		mealOrder.save(flush: true)
		
		agregator.addToMeals(meal);
		agregator.save(flush: true);
		
		
        println "orderMealManually 2, Params:"+params
        render template: 'meals', model: [principal: springSecurityService.principal,
										  order: MealOrder.getCurrent()]
    }
	
	
	def orderPartOfMeal(){
		 println "enter getPartOfMeal, params: "+ params
		 def mealAgregator = MealAgregator.get(params.agregatorId)
		 
		 def user = User.get(getLoggedUserId())
		 
		 def userAlreadyOrderedPartOfThisMeal = false
		 def meal1;
		 mealAgregator.meals.each(){
			 if(it.user.id ==  user.id){
				 userAlreadyOrderedPartOfThisMeal = true
				 meal1 = it
			 }
		 }
		 if(userAlreadyOrderedPartOfThisMeal){
			 meal1.partOfMeal = meal1.partOfMeal + params.partOfMeal.toInteger();
			 meal1.save(flush: true);
		 }else{

		 
			 def mealOrder = MealOrder.getCurrent()
			 
			 def partPrice = (mealAgregator.mealPrice * params.partOfMeal.toInteger()) / 8
			 
			 println "User.get(getLoggedUserId()): " + User.get(getLoggedUserId())
			 
			 
			 def meal = new Meal(
				 user: user,
				 date: new Date(),
				 approved: true,
				 agregator: mealAgregator,
				 partOfMeal: params.partOfMeal);
			 
	
			 user.addToOperations(meal)
			 if(!user.save(flush: true) ){
				 user.errors.allErrors.each {
					 println "errors: " + it
				 }
			 }
			 
			 mealAgregator.addToMeals(meal)
			 mealAgregator.save(flush: true)
		 
		 }
		 
         render template: 'meals', model: [principal: springSecurityService.principal,
								  order: MealOrder.getCurrent()]
		 
	}

    def orderMealOfDay(){
		
		def mealOrder = MealOrder.getCurrent()
		println "mealOrder : "+ mealOrder
		
		println "Order meal, User : "+ User.get(getLoggedUserId())
	        def meal = new Meal(
	                description:  params.id,
	                user: User.get(getLoggedUserId()) ,
	                amount: "13.99",
	                date: new Date(),
					approved: false,
					order: mealOrder);
	
		println "Order meal, User : "+ User.get(getLoggedUserId())
	
		def user = User.get(getLoggedUserId())
		user.addToOperations(meal)
		user.save(flush: true)
		
		mealOrder.addToMeals(meal)
		mealOrder.save(flush: true)
		
        render template: 'meals', model: [principal: springSecurityService.principal,
										  order: MealOrder.getCurrent()]
    }

    def removeMeal(){
        def mealInstance = Meal.get(params.id)
		println "mealInstance: " + mealInstance
		
		if (mealInstance) {
			try {
				User u = User.findByOperation(mealInstance)
				if(u){
					u.operations.remove(mealInstance)
				}
				MealOrder order = MealOrder.findByMeal(mealInstance)
				println "order: " + order
				if(order){
					order.meals.remove(mealInstance)
				}
				mealInstance.delete(flush: true)
			}catch(Exception ex){
				println "Exception occurred: "+ ex
			}
		}
        render template: 'meals', model: [principal: springSecurityService.principal,
										  order: MealOrder.getCurrent()]
    }

    def getCart(){
        println "enter getCart()"
        def user = new User(username: "user", password: "pass")
        def cart = new Cart()
        def meals
		
		def MealOrder mealOrder =  MealOrder.get(1)
		if(mealOrder != null){
			println "from order"
			meals = mealOrder.meals
		}else{
			println "from all"
			meals =  Meal.list(sort: "name", order: "asc")	
		}
		

        for(Meal meal: meals){
            println "meal name: " + meal.description
            def pos1 = new CartPosition(meal: meal)
            cart.addToPositions(pos1)
        }
        for(CartPosition pos: cart.positions){
            println "  meal name: " + pos.meal.description
        }
        cart
    }

    def List<Meal> getDayliMeals(){
		println "enter getDayliMeals"
        try{
            @Grab(group='org.ccil.cowan.tagsoup',module='tagsoup', version='1.2' )
            def tagsoupParser = new org.ccil.cowan.tagsoup.Parser()
            def slurper = new XmlSlurper(tagsoupParser)
            def bodyNode = slurper.parse("http://www.peperone.pl/")

            NodeChild table = bodyNode.'**'.find{ it.@class == 'contentpaneopen'}

            List<Meal> meals = new ArrayList<Meal>();


			//	piatek
			for(int i = 0; i <= 6; ++i){
				
				def mealName =  table[0].children[0].children[0].children[3+2*i].text().split("\\.")[1].trim()
				def mealPrice = "13.00"
				Meal meal = new Meal(description:  mealName, amount: mealPrice)
				meals.add(meal);
			}
	
//            if(table[0].children[0].children[0].children[3] != null){
//
//                int mealCounter = (table[0].children[0].children[0].children[3].children().size()-3)/2
//				println "meal counter : " + mealCounter
//				
//                for(int i = 0; i <= mealCounter; ++i){
//
//                    def mealName =  table[0].children[0].children[0].children[3].children()[i*2].text()
//                    def mealPrice = 14
//                    MealOrder meal = new MealOrder(name:  mealName, price: mealPrice)
//                    meals.add(meal);
//                }
//            }
            meals
        }catch (Exception ex){
            println "ex: "+ ex
            peperoneMealsMock()
        }

    }


    def List<Meal> getSalats(){

//        bodyNode = slurper.parse("http://www.peperone.pl/salaty-menu-all")
//
//        table = bodyNode.'**'.find{ it.@class == 'contentpaneopen'}



//        println "children: "+ table[0].children[0].children[0].children[2].children[0].children[1].children().size();

        //  number
        //       println "node: "+ table[0].children[0].children[0].children[2].children[0].children[1].children[0].children[0].text();

        //  name
        //       println "name: "+ table[0].children[0].children[0].children[2].children[0].children[1].children[0].children[1].text();

        //  pice
        //       println "price: "+ table[0].children[0].children[0].children[2].children[0].children[1].children[0].children[2].text();
//
//        if(table[0].children[0].children[0].children[2].children[0].children[1] != null){
//
//            int mealCounter = table[0].children[0].children[0].children[2].children[0].children[1].children().size()
//
//            List<MealOrder> salats = new ArrayList<MealOrder>();
//            for(int i = 0; i <= 5; ++i){
//                def mealName =  table[0].children[0].children[0].children[2].children[0].children[1].children[i].children[1].text()
//     //           println "mael " + i + " is" + mealName
//                def mealPrice = table[0].children[0].children[0].children[2].children[0].children[1].children[i].children[2].text()
//                MealOrder meal = new MealOrder(name:  mealName, price: mealPrice)
//                salats.add(meal);
//            }
//            [salats: salats]
//        }else{
//            [info: "Błąd parsowania strony, uzupełnij zamówienie ręcznie"]
//        }
         null
    }
    


    def peperoneMealsMock(){
	List<Meal> meals = new ArrayList<Meal>()
	 for(int i = 0; i <= 6; ++i){
                def mealName = "Obiadek " + i 
                def mealPrice = 14
                Meal meal = new Meal(description:  mealName, price: mealPrice)
                meals.add(meal)
         }
         meals
    }

    def getLoggedUserId(){
        def principal = springSecurityService.principal
        principal.id
    }

}

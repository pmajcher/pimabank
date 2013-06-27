package com.cloudfoundry.pimabank

import groovy.util.slurpersupport.NodeChild
import org.springframework.web.servlet.ModelAndView


class OrderPeperoneController {

    def springSecurityService

    def index() {
         [	meals: getDayliMeals(),
			principal: springSecurityService.principal,
			order: MealOrder.getCurrent()]
    }


    def orderMealManually(){
		
		def mealOrder = MealOrder.getCurrent()
		
        def meal = new Meal(
                        description:  params.what,
                        user: User.get(getLoggedUserId()),
                        amount: params.price,
                        date: new Date(),
						approved: false,
						order: mealOrder);

        def user = User.get(getLoggedUserId())
        user.addToOperations(meal)
        user.save(flush: true)

		mealOrder.addToMeals(meal)
		mealOrder.save(flush: true)
		
        println "orderMealManually 2, Params:"+params
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

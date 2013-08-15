package tk.pimabank

import groovy.util.slurpersupport.NodeChild
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.support.RequestContextUtils
import java.text.NumberFormat

import tk.pimabank.data.MealOrderState


class OrderPeperoneController {

    def springSecurityService

    def index() {
         [	principal: springSecurityService.principal,
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

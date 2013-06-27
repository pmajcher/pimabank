package com.cloudfoundry.pimabank

import java.util.List;

class MealOrder {

	Date date
	boolean open
	
   	List meals
    static hasMany = [meals: Meal]
	
    static constraints = {
    }
	
	static MealOrder findByMeal(Meal meal){
		def criteria = MealOrder.createCriteria()
		def result = criteria.get {
			meals {
				idEq(meal.id)
			}
		}
		return result
	}
	
	static MealOrder getCurrent(){
		
		def now = new Date()
		def mealOrder = MealOrder.findByDateBetween(getBeginOfToday(), now)

		if(mealOrder){
			return mealOrder
		}
		new MealOrder(date: new Date(), open: true).save(flush: true)		
	}
	
	static Date getBeginOfToday(){	
		new Date().clearTime()	
	}
	
}

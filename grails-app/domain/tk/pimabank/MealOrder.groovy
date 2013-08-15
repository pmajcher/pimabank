package tk.pimabank

import java.util.List;
import tk.pimabank.data.MealOrderState

class MealOrder {

	Date dateCreated
	//	discount in percent [%]
	int discount
	MealOrderState state
	
//   	List meals
	List mealAgregators
    static hasMany = [mealAgregators: MealAgregator]
	
    static constraints = {
    }
	

	
	static MealAgregator findByMealAgregator(MealAgregator mealAgregator){
		def criteria = MealAgregator.createCriteria()
		def result = criteria.get {
			mealAgregators {
				idEq(mealAgregator.id)
			}
		}
		return result
	}
	
	static MealOrder getCurrent(){
		
		def now = new Date()
		def mealOrder = MealOrder.findByDateCreatedBetween(getBeginOfToday(), now)
		return mealOrder	
	}
	
	static Date getBeginOfToday(){	
		new Date().clearTime()	
	}
	
}

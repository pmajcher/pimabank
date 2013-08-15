package tk.pimabank

import java.util.List;

class MealAgregator {

	List meals
	static hasMany = [meals: Meal]
	
	def BigDecimal mealPrice
	def String mealName
	
    static constraints = {
    }
	
	def getFreeParts(){
		def orderedParts = 0
		meals.each() {
			orderedParts +=it.partOfMeal
		}
		return 8 - orderedParts
	}
}

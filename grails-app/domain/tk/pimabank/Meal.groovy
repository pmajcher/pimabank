package tk.pimabank

class Meal extends Operation{

	static belongsTo = [agregator: MealAgregator]
	
	//	optional 
	def int partOfMeal	//	partOfMeal = [1..8] - 1: 1/8 
	
}

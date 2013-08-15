package tk.pimabank.admin

import tk.pimabank.Meal;
import tk.pimabank.User;
import tk.pimabank.Refund;
import tk.pimabank.MealOrder;
import tk.pimabank.data.MealOrderState;


class AdminController {

	def springSecurityService
	
    def index() { 
		def userId = springSecurityService.principal.id
		def user = User.get(userId)
		def users = User.getAll()
		def totalAmount = 0
		def totalNotApproved = 0
		users.each(){
			totalAmount += it.getAccountState()[0]
			totalNotApproved += it.getAccountState()[1]
		}
		
		
		[user: user,
		 order: MealOrder.getCurrent(),
		 users: users,
		 totalAmount: totalAmount,
		 totalNotApproved: totalNotApproved]
	}
	
	def closeOrder(){
		println "enter closeOrder"
		def currentOrder = MealOrder.getCurrent()
		currentOrder.state = MealOrderState.CLOSE
		currentOrder.save(flush: true)
		
		render currentOrder.state.name()
	}
	
	def openOrder(){
		println "enter openOrder"
		def currentOrder = MealOrder.getCurrent()
		currentOrder.state = MealOrderState.OPEN
		currentOrder.save(flush: true)
		
		render currentOrder.state.name()
	}
	
	def arrivedOrder(){
		println "enter orderArrived"
		def currentOrder = MealOrder.getCurrent()
		currentOrder.state = MealOrderState.ARRIVED
		currentOrder.save(flush: true)
		
		User.getAll().each() {
			def BigDecimal totalAmount = 0;
			def user = it
			currentOrder.mealAgregators.each { agregator ->
				agregator.meals.each { meal ->
					if(meal.user.equals(user)){
						totalAmount += (agregator.mealPrice * meal.partOfMeal)/8
					}
				}

			}
			if(totalAmount != 0){
				def float discount = (totalAmount * currentOrder.discount)/100
				def roundedDiscount = Math.floor(discount*2)/2f
				printf " roundedDiscount: "+roundedDiscount
				if (roundedDiscount != 0){
					printf "user: " + user.username
					def refund = new Refund(	
								user: user, 
								amount: roundedDiscount,
								approved: true,
								description: "Discount "+currentOrder.discount+"%")
				

						user.addToOperations(refund)
						user.save(flush: true)
				
				}
				
			}
			printf "user: " + user.username +" totalAmount: "+totalAmount
		}
					
		render currentOrder.state.name()
	}
	

	
	def createOrder(){
		new MealOrder(state: MealOrderState.OPEN, discount: 10).save(flush: true)
		 redirect(action: "index")
	}
	
	def createEmail(){
		
		def StringBuilder email = new StringBuilder();
		email.append("Witam, \n\n")
		email.append("chciałbym złożyć zamówienie\n\n\n")


		MealOrder.getCurrent().meals.eachWithIndex() {
			 obj, i -> email.append("${i + 1}. ${obj.description}\n");
		}; 
		
		email.append("\n\nKontakt:\n");
		email.append("ul. Bociana 22A\n");
		email.append("SoftwareMind\n\n");
		
		email.append("tel.: 511326258\n\n");
		
		
		email.append("Pozdrawiam\n");
		email.append("Piotr Majcher\n");
		
		render email.toString()
	}

	// 2013-07-30 - jest różnica wynosząca 9zł - całe refundy
	// 2013-07-31 - ręcznie wyszło 81,03, z selecta - 73,03
}

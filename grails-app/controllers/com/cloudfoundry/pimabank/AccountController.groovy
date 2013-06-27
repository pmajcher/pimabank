package com.cloudfoundry.pimabank

class AccountController {

	def springSecurityService

	def index() {
		def userId = springSecurityService.principal.id
		def user = User.get(userId)

		def BigDecimal amount = 0
		Operation.findAllByUserAndApprovedLike(user, true).each(){
			println "it: " + it
			if(it != null){
				switch (it) {
					case Meal:
					case Loan:
						amount -= it.amount
					break;
					case Payment:
						amount += it.amount
					break;
				}
				
			}
		}
		[user: user, amount:amount, isOrderOpen: MealOrder.getCurrent().isOpen()]
	}
	
	def pay(){
		
		println "enter pay" + params
		def payment = new Payment(
				user: User.get(getLoggedUserId()),
				amount: params.amount,
				date: new Date(),
				approved: false,
				description: params.desc);

		def user = User.get(getLoggedUserId())
		user.addToOperations(payment)
		user.save()
		redirect(action: "index", params: [info: "Czekaj na potwierdzenie kredytu"])
		
	}
	
	def getLoan(){
		println "enter get loan" + params
		def loan = new Loan(
				user: User.get(getLoggedUserId()),
				amount: params.amount,
				date: new Date(),
				approved: false,
				description: params.desc);

		def user = User.get(getLoggedUserId())
		user.addToOperations(loan)
		user.save()
	}
	
	def getLoggedUserId(){
		springSecurityService.principal.id
	}
	
}

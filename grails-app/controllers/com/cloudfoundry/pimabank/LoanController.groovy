package com.cloudfoundry.pimabank

class LoanController {

	def springSecurityService


	def index() {
		[info: params.info]
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

		redirect(action: "index", params: [info: "Czekaj na potwierdzenie kredytu"])
	}

	def getLoggedUserId(){
		springSecurityService.principal.id
	}
}

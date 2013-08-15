package tk.pimabank

import tk.pimabank.utils.PimaBankUtils;

class LoanController {

	def springSecurityService

	def index() {
	}


	def getLoan(){
		println "enter get loan" + params

		double price = PimaBankUtils.parsePriceFromInput(params.amount)
		
		def loan = new Loan(
				user: User.get(getLoggedUserId()),
				amount: price,
				date: new Date(),
				approved: false,
				description: params.desc);

		def user = User.get(getLoggedUserId())
		user.addToOperations(loan)
		user.save()

		redirect(action: "index")
	}

	def getLoggedUserId(){
		springSecurityService.principal.id
	}
}

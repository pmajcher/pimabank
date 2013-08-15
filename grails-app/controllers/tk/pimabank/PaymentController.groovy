package tk.pimabank

class PaymentController {

	def springSecurityService
	
    def index() { }
	
	def pay(){
		
		println "enter pay" + params
		
		def amount = params.amount.replace(",", ".")
		def double d = Double.parseDouble(amount)
		printf "amount: "+amount
		printf "d: "+d
		
		def payment = new Payment(
				user: User.get(getLoggedUserId()),
				amount: d,
				date: new Date(),
				approved: false,
				description: params.desc);
		
			
		def user = User.get(getLoggedUserId())
		user.addToOperations(payment)
		user.save()
		
	//	println "payment: " + payment.toString()
		
	//	redirect(action: "index", params: [info: "Czekaj na potwierdzenie kredytu"])
		
		render "Czekaj na potwierdzenie"
	}
	
	def getLoggedUserId(){
		springSecurityService.principal.id
	}
}

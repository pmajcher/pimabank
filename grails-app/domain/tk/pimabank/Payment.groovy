package tk.pimabank

class Payment extends Operation{
		
	
    static constraints = {
    }

	def beforeValidate() {
		println "enter payment validator"
		if(amount < 0){
			errors.rejectValue("amount", "payment.amount.validate.negative")
		}
	}
	
}

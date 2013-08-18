package tk.pimabank

class Operation {

    User user
    BigDecimal amount
	Date dateCreated
	boolean approved
	String description
	
	static constraints = {
		description nullable: true
		amount nullable: true
	}
	
	def beforeValidate() {
		println "enter operation validate"
		if(amount != null && amount < 0){
			errors.rejectValue("amount", "transfer.amount.validator.invalid")
		}
	}
}

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
}

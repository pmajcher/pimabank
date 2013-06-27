package com.cloudfoundry.pimabank

class Operation {

    User user
    BigDecimal amount
    Date date
	boolean approved
	String description

    static constraints = {
    }
}

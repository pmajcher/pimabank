package com.cloudfoundry.pimabank

import javax.swing.LookAndFeel

class OperationDTO {

    long id
	String username
    OperationType operationType
    String description
    BigDecimal amount
    Date date
	boolean approved
	

    def OperationDTO(Operation operation){
        username = operation.user.username
		id = operation.id
		approved = operation.approved
		
        switch (operation) {
            case Meal:
                operationType = OperationType.PURCHASE
                description =  ((Meal)operation).description
                break;
            case Loan:
                operationType = OperationType.LOAN
                description =  ((Loan)operation).description
                break;
			case Payment:
				operationType = OperationType.PAYMENT
				description =  ((Payment)operation).description
				break;
        }

        date = operation.date
        amount = operation.amount

    }
}

package tk.pimabank.data

import javax.swing.LookAndFeel
import tk.pimabank.Operation
import tk.pimabank.Meal
import tk.pimabank.Loan
import tk.pimabank.Payment
import tk.pimabank.Refund
import tk.pimabank.Transfer

class OperationDTO {

    long id
	String username
    OperationType operationType
    String description
    BigDecimal amount
    Date dateCreated
	boolean approved
	

    def OperationDTO(Operation operation){
        username = operation.user.username
		id = operation.id
		approved = operation.approved
		
        switch (operation) {
            case Meal:
                operationType = OperationType.PURCHASE
				if(operation.agregator != null){
					amount = ( operation.agregator.mealPrice * operation.partOfMeal ) / 8
					description = operation.agregator.mealName
				}else{
					amount = operation.amount
					description = operation.description
				}
				break;
            case Loan:
                operationType = OperationType.LOAN
				amount = operation.amount
				description = operation.description
				break;
			case Payment:
				operationType = OperationType.PAYMENT
				amount = operation.amount
				description = operation.description
				break;
			case Refund:
				operationType = OperationType.REFUND
				amount = operation.amount
				description = operation.description
				break;
			case Transfer:
				operationType = OperationType.TRANSFER
				amount = operation.amount
				description = operation.description
				break;
        }

        dateCreated = operation.dateCreated

    }
}

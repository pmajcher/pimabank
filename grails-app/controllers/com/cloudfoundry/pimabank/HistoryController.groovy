package com.cloudfoundry.pimabank

class HistoryController {

	def springSecurityService

	def index() {

		def user = User.get(springSecurityService.principal.id)
		def operationInstanceTotal = Operation.findAllByUser(user).size();
		params.max = 10;
		
		def dtoOperations = new ArrayList<OperationDTO>()
		def operations = Operation.findAllByUser(user, params)
		
		operations.each(){
			dtoOperations.add(new OperationDTO(it))
		}
		
		[	operations: dtoOperations,
			principal: springSecurityService.principal,
			operationInstanceTotal: operationInstanceTotal]
	}
}

package tk.pimabank

class HistoryController {

	def springSecurityService

	def index() {

		def user = User.get(springSecurityService.principal.id)
		
		def dtoOperations = new ArrayList<OperationDTO>()
		def operations = Operation.findAllByUser(user, [max : 15, 
														sort : "dateCreated", 
														order: "desc"])
		
		operations.each(){
			dtoOperations.add(new OperationDTO(it))
		}		
		[operations: dtoOperations]
	}
}

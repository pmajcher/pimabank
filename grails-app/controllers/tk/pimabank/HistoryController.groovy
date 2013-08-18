package tk.pimabank

import tk.pimabank.data.OperationDTO

class HistoryController {

	def springSecurityService

	def index() {

		def user = User.get(springSecurityService.principal.id)
		
		def dtoOperations = new ArrayList<OperationDTO>()
		
		def operations = Operation.findAll("from Operation as o where o.user=:user or o.recipient=:user",
			[user: user], [max: 15, sort: "dateCreated", order: "desc"])
		
		
		operations.each(){
			dtoOperations.add(new OperationDTO(it))
		}		
		[operations: dtoOperations]
	}
}

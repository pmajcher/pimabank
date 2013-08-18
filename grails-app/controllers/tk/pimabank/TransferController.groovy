package tk.pimabank

import org.apache.commons.lang.StringUtils;
import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

import tk.pimabank.utils.PimaBankUtils

class TransferController {

	def springSecurityService
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
		println "enter transfer index aaaa"
		
		User user = User.get(getLoggedUserId())
		
		def transfers = Transfer.findAll("from Transfer as t where t.recipient=:user and t.approved=false",
			[user: user]);
			
			[transfers: transfers, user: user]
		
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [transferInstanceList: Transfer.list(params), transferInstanceTotal: Transfer.count()]
    }

    def create() {
        [transferInstance: new Transfer(params)]
    }

    def save() {
        def transferInstance = new Transfer(params)
        if (!transferInstance.save(flush: true)) {
            render(view: "create", model: [transferInstance: transferInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'transfer.label', default: 'Transfer'), transferInstance.id])
        redirect(action: "show", id: transferInstance.id)
    }

    def show(Long id) {
        def transferInstance = Transfer.get(id)
        if (!transferInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'transfer.label', default: 'Transfer'), id])
            redirect(action: "list")
            return
        }

        [transferInstance: transferInstance]
    }

    def edit(Long id) {
        def transferInstance = Transfer.get(id)
        if (!transferInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'transfer.label', default: 'Transfer'), id])
            redirect(action: "list")
            return
        }

        [transferInstance: transferInstance]
    }

    def update(Long id, Long version) {
        def transferInstance = Transfer.get(id)
        if (!transferInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'transfer.label', default: 'Transfer'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (transferInstance.version > version) {
                transferInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'transfer.label', default: 'Transfer')] as Object[],
                          "Another user has updated this Transfer while you were editing")
                render(view: "edit", model: [transferInstance: transferInstance])
                return
            }
        }

        transferInstance.properties = params

        if (!transferInstance.save(flush: true)) {
            render(view: "edit", model: [transferInstance: transferInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'transfer.label', default: 'Transfer'), transferInstance.id])
        redirect(action: "show", id: transferInstance.id)
    }

    def delete(Long id) {
        def transferInstance = Transfer.get(id)
        if (!transferInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'transfer.label', default: 'Transfer'), id])
            redirect(action: "list")
            return
        }

        try {
            transferInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'transfer.label', default: 'Transfer'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'transfer.label', default: 'Transfer'), id])
            redirect(action: "show", id: id)
        }
    }
	
	def ajaxSave(){
		println "enter ajaxSave, params: " +params
		
		List<String> errorMessages = new ArrayList<String>()
		
		double amount = 0
		if(StringUtils.isEmpty(params.amount)){
			errorMessages.add(message(code: "transfer.amount.validate.empty"))
		}else{
			try{
				amount = PimaBankUtils.parsePriceFromInput(params.amount)
			} catch(NumberFormatException ex){
				errorMessages.add(message(code: "transfer.amount.validate.invalid"))
			}
		}
		Transfer transfer = new Transfer(
				user: User.get(getLoggedUserId()),
				amount: amount,
				date: new Date(),
				approved: false,
				description: params.description,
				recipient:User.get(params.recipient.id ))
		
		def user = User.get(getLoggedUserId())
		user.addToOperations(transfer)
		
		if(!transfer.validate() || !errorMessages.isEmpty()){			
			transfer.errors.allErrors.each {
				errorMessages.add(message(code: it.getCode()));
			}			
			render(status: 400, contentType: "text/json") {
				text = errorMessages
			}
			return
		}

		if (!user.save(flush: true)) {
			render(status: 400, text: message(code: "transfer.save.error")) 
			return
		}
		render(status: 200, text: message(code: "transfer.save.ok")) 
		return
	}
	
	def incommingTransfers(){
		println "enter incommingTransfers"
		render(template:"incommingTransfers",model:[transfers: Transfer.getAll()])
	}
	
	def approveTransfer(){
		
		println "enter approveTransfer, params: " + params
		
		Transfer transfer = Transfer.get(params.id)
		transfer.approved = true
		transfer.save(flush: true)
		
		User user = User.get(getLoggedUserId())
		
		def transfers = Transfer.findAll("from Transfer as t where t.recipient=:user and t.approved=false",
			[user: user])
		
		render template: 'incommingTransfers', model: [transfers: transfers, user:user]
		
	}
	
	private long getLoggedUserId(){
		springSecurityService.principal.id
	}
	
}

package tk.pimabank

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataIntegrityViolationException
import tk.pimabank.utils.PimaBankUtils


class PaymentController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]


	def springSecurityService
	
    def index() { }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [paymentInstanceList: Payment.list(params), paymentInstanceTotal: Payment.count()]
    }

    def create() {
        [paymentInstance: new Payment(params)]
    }

    def save() {
        def paymentInstance = new Payment(params)
        if (!paymentInstance.save(flush: true)) {
            render(view: "create", model: [paymentInstance: paymentInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'payment.label', default: 'Payment'), paymentInstance.id])
        redirect(action: "show", id: paymentInstance.id)
    }

    def show(Long id) {
        def paymentInstance = Payment.get(id)
        if (!paymentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'payment.label', default: 'Payment'), id])
            redirect(action: "list")
            return
        }

        [paymentInstance: paymentInstance]
    }

    def edit(Long id) {
        def paymentInstance = Payment.get(id)
        if (!paymentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'payment.label', default: 'Payment'), id])
            redirect(action: "list")
            return
        }

        [paymentInstance: paymentInstance]
    }

    def update(Long id, Long version) {
        def paymentInstance = Payment.get(id)
        if (!paymentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'payment.label', default: 'Payment'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (paymentInstance.version > version) {
                paymentInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'payment.label', default: 'Payment')] as Object[],
                          "Another user has updated this Payment while you were editing")
                render(view: "edit", model: [paymentInstance: paymentInstance])
                return
            }
        }

        paymentInstance.properties = params

        if (!paymentInstance.save(flush: true)) {
            render(view: "edit", model: [paymentInstance: paymentInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'payment.label', default: 'Payment'), paymentInstance.id])
        redirect(action: "show", id: paymentInstance.id)
    }

    def delete(Long id) {
        def paymentInstance = Payment.get(id)
        if (!paymentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'payment.label', default: 'Payment'), id])
            redirect(action: "list")
            return
        }

        try {
            paymentInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'payment.label', default: 'Payment'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'payment.label', default: 'Payment'), id])
            redirect(action: "show", id: id)
        }
    }
	
	def pay(){
		
		println "enter pay" + params
		
		List<String> errorMessages = new ArrayList<String>()
		
		double amount = 0
		if(StringUtils.isEmpty(params.amount)){
			errorMessages.add(message(code: "payment.amount.validate.empty"))
		}else{
			try{
				amount = PimaBankUtils.parsePriceFromInput(params.amount)
			} catch(NumberFormatException ex){
				errorMessages.add(message(code: "payment.amount.validate.invalid"))
			}
		}
		
		def payment = new Payment(
				user: User.get(getLoggedUserId()),
				amount: amount,
				date: new Date(),
				approved: false,
				description: params.desc);
		
			
		def user = User.get(getLoggedUserId())
		user.addToOperations(payment)
		
		
		
		
		if(!payment.validate() || !errorMessages.isEmpty()){
			payment.errors.allErrors.each {
				errorMessages.add(message(code: it.getCode()));
			}
			render(status: 400, contentType: "text/json") {
				text = errorMessages
			}
			return
		}
		
		if (!user.save(flush: true)) {
			render(status: 400, text: message(code: "payment.save.error"))
			return
		}
		render(status: 200, text: message(code: "payment.save.ok"))
		return

	}
	
	def getLoggedUserId(){
		springSecurityService.principal.id
	}
}

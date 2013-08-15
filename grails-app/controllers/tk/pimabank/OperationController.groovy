package tk.pimabank

import org.springframework.dao.DataIntegrityViolationException

class OperationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [operationInstanceList: Operation.list(params), operationInstanceTotal: Operation.count()]
    }

    def create() {
        [operationInstance: new Operation(params)]
    }

    def save() {
        def operationInstance = new Operation(params)
        if (!operationInstance.save(flush: true)) {
            render(view: "create", model: [operationInstance: operationInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'operation.label', default: 'Operation'), operationInstance.id])
        redirect(action: "show", id: operationInstance.id)
    }

    def show(Long id) {
        def operationInstance = Operation.get(id)
        if (!operationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'operation.label', default: 'Operation'), id])
            redirect(action: "list")
            return
        }

        [operationInstance: operationInstance]
    }

    def edit(Long id) {
        def operationInstance = Operation.get(id)
        if (!operationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'operation.label', default: 'Operation'), id])
            redirect(action: "list")
            return
        }

        [operationInstance: operationInstance]
    }

    def update(Long id, Long version) {
        def operationInstance = Operation.get(id)
        if (!operationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'operation.label', default: 'Operation'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (operationInstance.version > version) {
                operationInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'operation.label', default: 'Operation')] as Object[],
                          "Another user has updated this Operation while you were editing")
                render(view: "edit", model: [operationInstance: operationInstance])
                return
            }
        }

        operationInstance.properties = params

        if (!operationInstance.save(flush: true)) {
            render(view: "edit", model: [operationInstance: operationInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'operation.label', default: 'Operation'), operationInstance.id])
        redirect(action: "show", id: operationInstance.id)
    }

    def delete(Long id) {
        def operationInstance = Operation.get(id)
        if (!operationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'operation.label', default: 'Operation'), id])
            redirect(action: "list")
            return
        }

        try {
            operationInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'operation.label', default: 'Operation'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'operation.label', default: 'Operation'), id])
            redirect(action: "show", id: id)
        }
    }
	
	def approve() {
		println "enter approve, params: " + params
		def operationId = params.operationId
		Operation operation = Operation.get(operationId);
		operation.approved = true;
		operation.save(flush: true);
		
		return
	}
}

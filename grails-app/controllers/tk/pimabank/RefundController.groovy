package tk.pimabank

import org.springframework.dao.DataIntegrityViolationException

class RefundController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [refundInstanceList: Refund.list(params), refundInstanceTotal: Refund.count()]
    }

    def create() {
        [refundInstance: new Refund(params)]
    }

    def save() {
        def refundInstance = new Refund(params)
        if (!refundInstance.save(flush: true)) {
            render(view: "create", model: [refundInstance: refundInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'refund.label', default: 'Refund'), refundInstance.id])
        redirect(action: "show", id: refundInstance.id)
    }

    def show(Long id) {
        def refundInstance = Refund.get(id)
        if (!refundInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'refund.label', default: 'Refund'), id])
            redirect(action: "list")
            return
        }

        [refundInstance: refundInstance]
    }

    def edit(Long id) {
        def refundInstance = Refund.get(id)
        if (!refundInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'refund.label', default: 'Refund'), id])
            redirect(action: "list")
            return
        }

        [refundInstance: refundInstance]
    }

    def update(Long id, Long version) {
        def refundInstance = Refund.get(id)
        if (!refundInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'refund.label', default: 'Refund'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (refundInstance.version > version) {
                refundInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'refund.label', default: 'Refund')] as Object[],
                          "Another user has updated this Refund while you were editing")
                render(view: "edit", model: [refundInstance: refundInstance])
                return
            }
        }

        refundInstance.properties = params

        if (!refundInstance.save(flush: true)) {
            render(view: "edit", model: [refundInstance: refundInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'refund.label', default: 'Refund'), refundInstance.id])
        redirect(action: "show", id: refundInstance.id)
    }

    def delete(Long id) {
        def refundInstance = Refund.get(id)
        if (!refundInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'refund.label', default: 'Refund'), id])
            redirect(action: "list")
            return
        }

        try {
            refundInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'refund.label', default: 'Refund'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'refund.label', default: 'Refund'), id])
            redirect(action: "show", id: id)
        }
    }
}

package tk.pimabank

import org.springframework.dao.DataIntegrityViolationException

class BugReportController {

	def springSecurityService
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [bugReportInstanceList: BugReport.list(params), bugReportInstanceTotal: BugReport.count()]
    }

    def create() {
        [bugReportInstance: new BugReport(params)]
    }

    def save() {
        def bugReportInstance = new BugReport(params)
        if (!bugReportInstance.save(flush: true)) {
            render(view: "create", model: [bugReportInstance: bugReportInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'bugReport.label', default: 'BugReport'), bugReportInstance.id])
        redirect(action: "show", id: bugReportInstance.id)
    }

    def show(Long id) {
        def bugReportInstance = BugReport.get(id)
        if (!bugReportInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bugReport.label', default: 'BugReport'), id])
            redirect(action: "list")
            return
        }

        [bugReportInstance: bugReportInstance]
    }

    def edit(Long id) {
        def bugReportInstance = BugReport.get(id)
        if (!bugReportInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bugReport.label', default: 'BugReport'), id])
            redirect(action: "list")
            return
        }

        [bugReportInstance: bugReportInstance]
    }

    def update(Long id, Long version) {
        def bugReportInstance = BugReport.get(id)
        if (!bugReportInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bugReport.label', default: 'BugReport'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (bugReportInstance.version > version) {
                bugReportInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'bugReport.label', default: 'BugReport')] as Object[],
                          "Another user has updated this BugReport while you were editing")
                render(view: "edit", model: [bugReportInstance: bugReportInstance])
                return
            }
        }

        bugReportInstance.properties = params

        if (!bugReportInstance.save(flush: true)) {
            render(view: "edit", model: [bugReportInstance: bugReportInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'bugReport.label', default: 'BugReport'), bugReportInstance.id])
        redirect(action: "show", id: bugReportInstance.id)
    }

    def delete(Long id) {
        def bugReportInstance = BugReport.get(id)
        if (!bugReportInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bugReport.label', default: 'BugReport'), id])
            redirect(action: "list")
            return
        }

        try {
            bugReportInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'bugReport.label', default: 'BugReport'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'bugReport.label', default: 'BugReport'), id])
            redirect(action: "show", id: id)
        }
    }
	
	def createAjax(){
		printf "createAjax, params: " + params
		def user = User.get(springSecurityService.principal.id)
		
		def bugReport = new BugReport(	user: user,
										description: params. description,
										done: false)
		bugReport.save(flush: true)
		
		render "Bug report created"
	}
	
}

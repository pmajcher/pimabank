package tk.pimabank

import org.springframework.dao.DataIntegrityViolationException

class MealController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [mealInstanceList: Meal.list(params), mealInstanceTotal: Meal.count()]
    }

    def create() {
        [mealInstance: new Meal(params)]
    }

    def save() {
        def mealInstance = new Meal(params)
        if (!mealInstance.save(flush: true)) {
            render(view: "create", model: [mealInstance: mealInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'meal.label', default: 'Meal'), mealInstance.id])
        redirect(action: "show", id: mealInstance.id)
    }

    def show(Long id) {
        def mealInstance = Meal.get(id)
        if (!mealInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'meal.label', default: 'Meal'), id])
            redirect(action: "list")
            return
        }

        [mealInstance: mealInstance]
    }

    def edit(Long id) {
        def mealInstance = Meal.get(id)
        if (!mealInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'meal.label', default: 'Meal'), id])
            redirect(action: "list")
            return
        }

        [mealInstance: mealInstance]
    }

    def update(Long id, Long version) {
        def mealInstance = Meal.get(id)
        if (!mealInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'meal.label', default: 'Meal'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (mealInstance.version > version) {
                mealInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'meal.label', default: 'Meal')] as Object[],
                          "Another user has updated this Meal while you were editing")
                render(view: "edit", model: [mealInstance: mealInstance])
                return
            }
        }

        mealInstance.properties = params

        if (!mealInstance.save(flush: true)) {
            render(view: "edit", model: [mealInstance: mealInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'meal.label', default: 'Meal'), mealInstance.id])
        redirect(action: "show", id: mealInstance.id)
    }

    def delete(Long id) {
        def mealInstance = Meal.get(id)
        if (!mealInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'meal.label', default: 'Meal'), id])
            redirect(action: "list")
            return
        }

        try {
            mealInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'meal.label', default: 'Meal'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'meal.label', default: 'Meal'), id])
            redirect(action: "show", id: id)
        }
    }
}

package tk.pimabank



import org.junit.*
import grails.test.mixin.*

@TestFor(MealController)
@Mock(Meal)
class MealControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/meal/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.mealInstanceList.size() == 0
        assert model.mealInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.mealInstance != null
    }

    void testSave() {
        controller.save()

        assert model.mealInstance != null
        assert view == '/meal/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/meal/show/1'
        assert controller.flash.message != null
        assert Meal.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/meal/list'

        populateValidParams(params)
        def meal = new Meal(params)

        assert meal.save() != null

        params.id = meal.id

        def model = controller.show()

        assert model.mealInstance == meal
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/meal/list'

        populateValidParams(params)
        def meal = new Meal(params)

        assert meal.save() != null

        params.id = meal.id

        def model = controller.edit()

        assert model.mealInstance == meal
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/meal/list'

        response.reset()

        populateValidParams(params)
        def meal = new Meal(params)

        assert meal.save() != null

        // test invalid parameters in update
        params.id = meal.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/meal/edit"
        assert model.mealInstance != null

        meal.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/meal/show/$meal.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        meal.clearErrors()

        populateValidParams(params)
        params.id = meal.id
        params.version = -1
        controller.update()

        assert view == "/meal/edit"
        assert model.mealInstance != null
        assert model.mealInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/meal/list'

        response.reset()

        populateValidParams(params)
        def meal = new Meal(params)

        assert meal.save() != null
        assert Meal.count() == 1

        params.id = meal.id

        controller.delete()

        assert Meal.count() == 0
        assert Meal.get(meal.id) == null
        assert response.redirectedUrl == '/meal/list'
    }
}

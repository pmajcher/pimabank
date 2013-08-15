package tk.pimabank



import org.junit.*
import grails.test.mixin.*

@TestFor(OperationController)
@Mock(Operation)
class OperationControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/operation/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.operationInstanceList.size() == 0
        assert model.operationInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.operationInstance != null
    }

    void testSave() {
        controller.save()

        assert model.operationInstance != null
        assert view == '/operation/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/operation/show/1'
        assert controller.flash.message != null
        assert Operation.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/operation/list'

        populateValidParams(params)
        def operation = new Operation(params)

        assert operation.save() != null

        params.id = operation.id

        def model = controller.show()

        assert model.operationInstance == operation
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/operation/list'

        populateValidParams(params)
        def operation = new Operation(params)

        assert operation.save() != null

        params.id = operation.id

        def model = controller.edit()

        assert model.operationInstance == operation
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/operation/list'

        response.reset()

        populateValidParams(params)
        def operation = new Operation(params)

        assert operation.save() != null

        // test invalid parameters in update
        params.id = operation.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/operation/edit"
        assert model.operationInstance != null

        operation.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/operation/show/$operation.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        operation.clearErrors()

        populateValidParams(params)
        params.id = operation.id
        params.version = -1
        controller.update()

        assert view == "/operation/edit"
        assert model.operationInstance != null
        assert model.operationInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/operation/list'

        response.reset()

        populateValidParams(params)
        def operation = new Operation(params)

        assert operation.save() != null
        assert Operation.count() == 1

        params.id = operation.id

        controller.delete()

        assert Operation.count() == 0
        assert Operation.get(operation.id) == null
        assert response.redirectedUrl == '/operation/list'
    }
}

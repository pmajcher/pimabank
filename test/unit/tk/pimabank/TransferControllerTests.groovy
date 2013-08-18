package tk.pimabank



import org.junit.*
import grails.test.mixin.*

@TestFor(TransferController)
@Mock(Transfer)
class TransferControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/transfer/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.transferInstanceList.size() == 0
        assert model.transferInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.transferInstance != null
    }

    void testSave() {
        controller.save()

        assert model.transferInstance != null
        assert view == '/transfer/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/transfer/show/1'
        assert controller.flash.message != null
        assert Transfer.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/transfer/list'

        populateValidParams(params)
        def transfer = new Transfer(params)

        assert transfer.save() != null

        params.id = transfer.id

        def model = controller.show()

        assert model.transferInstance == transfer
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/transfer/list'

        populateValidParams(params)
        def transfer = new Transfer(params)

        assert transfer.save() != null

        params.id = transfer.id

        def model = controller.edit()

        assert model.transferInstance == transfer
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/transfer/list'

        response.reset()

        populateValidParams(params)
        def transfer = new Transfer(params)

        assert transfer.save() != null

        // test invalid parameters in update
        params.id = transfer.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/transfer/edit"
        assert model.transferInstance != null

        transfer.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/transfer/show/$transfer.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        transfer.clearErrors()

        populateValidParams(params)
        params.id = transfer.id
        params.version = -1
        controller.update()

        assert view == "/transfer/edit"
        assert model.transferInstance != null
        assert model.transferInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/transfer/list'

        response.reset()

        populateValidParams(params)
        def transfer = new Transfer(params)

        assert transfer.save() != null
        assert Transfer.count() == 1

        params.id = transfer.id

        controller.delete()

        assert Transfer.count() == 0
        assert Transfer.get(transfer.id) == null
        assert response.redirectedUrl == '/transfer/list'
    }
}

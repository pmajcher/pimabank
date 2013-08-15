package tk.pimabank



import org.junit.*
import grails.test.mixin.*

@TestFor(RefundController)
@Mock(Refund)
class RefundControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/refund/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.refundInstanceList.size() == 0
        assert model.refundInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.refundInstance != null
    }

    void testSave() {
        controller.save()

        assert model.refundInstance != null
        assert view == '/refund/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/refund/show/1'
        assert controller.flash.message != null
        assert Refund.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/refund/list'

        populateValidParams(params)
        def refund = new Refund(params)

        assert refund.save() != null

        params.id = refund.id

        def model = controller.show()

        assert model.refundInstance == refund
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/refund/list'

        populateValidParams(params)
        def refund = new Refund(params)

        assert refund.save() != null

        params.id = refund.id

        def model = controller.edit()

        assert model.refundInstance == refund
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/refund/list'

        response.reset()

        populateValidParams(params)
        def refund = new Refund(params)

        assert refund.save() != null

        // test invalid parameters in update
        params.id = refund.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/refund/edit"
        assert model.refundInstance != null

        refund.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/refund/show/$refund.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        refund.clearErrors()

        populateValidParams(params)
        params.id = refund.id
        params.version = -1
        controller.update()

        assert view == "/refund/edit"
        assert model.refundInstance != null
        assert model.refundInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/refund/list'

        response.reset()

        populateValidParams(params)
        def refund = new Refund(params)

        assert refund.save() != null
        assert Refund.count() == 1

        params.id = refund.id

        controller.delete()

        assert Refund.count() == 0
        assert Refund.get(refund.id) == null
        assert response.redirectedUrl == '/refund/list'
    }
}

package tk.pimabank


import org.junit.*
import grails.test.mixin.*

@TestFor(BugReportController)
@Mock(BugReport)
class BugReportControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/bugReport/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.bugReportInstanceList.size() == 0
        assert model.bugReportInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.bugReportInstance != null
    }

    void testSave() {
        controller.save()

        assert model.bugReportInstance != null
        assert view == '/bugReport/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/bugReport/show/1'
        assert controller.flash.message != null
        assert BugReport.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/bugReport/list'

        populateValidParams(params)
        def bugReport = new BugReport(params)

        assert bugReport.save() != null

        params.id = bugReport.id

        def model = controller.show()

        assert model.bugReportInstance == bugReport
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/bugReport/list'

        populateValidParams(params)
        def bugReport = new BugReport(params)

        assert bugReport.save() != null

        params.id = bugReport.id

        def model = controller.edit()

        assert model.bugReportInstance == bugReport
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/bugReport/list'

        response.reset()

        populateValidParams(params)
        def bugReport = new BugReport(params)

        assert bugReport.save() != null

        // test invalid parameters in update
        params.id = bugReport.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/bugReport/edit"
        assert model.bugReportInstance != null

        bugReport.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/bugReport/show/$bugReport.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        bugReport.clearErrors()

        populateValidParams(params)
        params.id = bugReport.id
        params.version = -1
        controller.update()

        assert view == "/bugReport/edit"
        assert model.bugReportInstance != null
        assert model.bugReportInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/bugReport/list'

        response.reset()

        populateValidParams(params)
        def bugReport = new BugReport(params)

        assert bugReport.save() != null
        assert BugReport.count() == 1

        params.id = bugReport.id

        controller.delete()

        assert BugReport.count() == 0
        assert BugReport.get(bugReport.id) == null
        assert response.redirectedUrl == '/bugReport/list'
    }
}

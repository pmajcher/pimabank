<%@ page import="tk.pimabank.BugReport" %>



<div class="fieldcontain ${hasErrors(bean: bugReportInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="bugReport.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${bugReportInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bugReportInstance, field: 'done', 'error')} ">
	<label for="done">
		<g:message code="bugReport.done.label" default="Done" />
		
	</label>
	<g:checkBox name="done" value="${bugReportInstance?.done}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bugReportInstance, field: 'user', 'error')} required">
	<label for="user">
		<g:message code="bugReport.user.label" default="User" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="user" name="user.id" from="${tk.pimabank.User.list()}" optionKey="id" required="" value="${bugReportInstance?.user?.id}" class="many-to-one"/>
</div>


<%@ page import="com.cloudfoundry.pimabank.Operation" %>



<div class="fieldcontain ${hasErrors(bean: operationInstance, field: 'amount', 'error')} required">
	<label for="amount">
		<g:message code="operation.amount.label" default="Amount" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="amount" value="${fieldValue(bean: operationInstance, field: 'amount')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: operationInstance, field: 'approved', 'error')} ">
	<label for="approved">
		<g:message code="operation.approved.label" default="Approved" />
		
	</label>
	<g:checkBox name="approved" value="${operationInstance?.approved}" />
</div>

<div class="fieldcontain ${hasErrors(bean: operationInstance, field: 'date', 'error')} required">
	<label for="date">
		<g:message code="operation.date.label" default="Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="date" precision="day"  value="${operationInstance?.date}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: operationInstance, field: 'user', 'error')} required">
	<label for="user">
		<g:message code="operation.user.label" default="User" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="user" name="user.id" from="${com.cloudfoundry.pimabank.User.list()}" optionKey="id" required="" value="${operationInstance?.user?.id}" class="many-to-one"/>
</div>


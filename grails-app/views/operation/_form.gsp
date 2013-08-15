<%@ page import="tk.pimabank.Operation" %>



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

<div class="fieldcontain ${hasErrors(bean: operationInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="operation.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${operationInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: operationInstance, field: 'user', 'error')} required">
	<label for="user">
		<g:message code="operation.user.label" default="User" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="user" name="user.id" from="${tk.pimabank.User.list()}" optionKey="id" required="" value="${operationInstance?.user?.id}" class="many-to-one"/>
</div>


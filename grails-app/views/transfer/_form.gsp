<%@ page import="tk.pimabank.Transfer" %>



<div class="fieldcontain ${hasErrors(bean: transferInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="transfer.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${transferInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: transferInstance, field: 'amount', 'error')} ">
	<label for="amount">
		<g:message code="transfer.amount.label" default="Amount" />
		
	</label>
	<g:field name="amount" value="${fieldValue(bean: transferInstance, field: 'amount')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: transferInstance, field: 'approved', 'error')} ">
	<label for="approved">
		<g:message code="transfer.approved.label" default="Approved" />
		
	</label>
	<g:checkBox name="approved" value="${transferInstance?.approved}" />
</div>

<div class="fieldcontain ${hasErrors(bean: transferInstance, field: 'recipient', 'error')} required">
	<label for="recipient">
		<g:message code="transfer.recipient.label" default="Recipient" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="recipient" name="recipient.id" from="${tk.pimabank.User.list()}" optionKey="id" required="" value="${transferInstance?.recipient?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: transferInstance, field: 'user', 'error')} required">
	<label for="user">
		<g:message code="transfer.user.label" default="User" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="user" name="user.id" from="${tk.pimabank.User.list()}" optionKey="id" required="" value="${transferInstance?.user?.id}" class="many-to-one"/>
</div>


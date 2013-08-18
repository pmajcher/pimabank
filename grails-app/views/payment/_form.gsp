<%@ page import="tk.pimabank.Payment" %>



<div class="fieldcontain ${hasErrors(bean: paymentInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="payment.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${paymentInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: paymentInstance, field: 'amount', 'error')} ">
	<label for="amount">
		<g:message code="payment.amount.label" default="Amount" />
		
	</label>
	<g:field name="amount" value="${fieldValue(bean: paymentInstance, field: 'amount')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: paymentInstance, field: 'approved', 'error')} ">
	<label for="approved">
		<g:message code="payment.approved.label" default="Approved" />
		
	</label>
	<g:checkBox name="approved" value="${paymentInstance?.approved}" />
</div>

<div class="fieldcontain ${hasErrors(bean: paymentInstance, field: 'user', 'error')} required">
	<label for="user">
		<g:message code="payment.user.label" default="User" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="user" name="user.id" from="${tk.pimabank.User.list()}" optionKey="id" required="" value="${paymentInstance?.user?.id}" class="many-to-one"/>
</div>


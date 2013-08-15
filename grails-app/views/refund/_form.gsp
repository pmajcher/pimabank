<%@ page import="tk.pimabank.Refund" %>



<div class="fieldcontain ${hasErrors(bean: refundInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="refund.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${refundInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: refundInstance, field: 'amount', 'error')} ">
	<label for="amount">
		<g:message code="refund.amount.label" default="Amount" />
		
	</label>
	<g:field name="amount" value="${fieldValue(bean: refundInstance, field: 'amount')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: refundInstance, field: 'approved', 'error')} ">
	<label for="approved">
		<g:message code="refund.approved.label" default="Approved" />
		
	</label>
	<g:checkBox name="approved" value="${refundInstance?.approved}" />
</div>

<div class="fieldcontain ${hasErrors(bean: refundInstance, field: 'user', 'error')} required">
	<label for="user">
		<g:message code="refund.user.label" default="User" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="user" name="user.id" from="${tk.pimabank.User.list()}" optionKey="id" required="" value="${refundInstance?.user?.id}" class="many-to-one"/>
</div>


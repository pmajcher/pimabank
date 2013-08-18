<%@ page import="tk.pimabank.Transfer" %>



<div class="fieldcontain ${hasErrors(bean: transferInstance, field: 'amount', 'error')} ">
    <label for="amount">
        <g:message code="transfer.amount.label" default="Amount" />
        <span class="required-indicator">*</span>
        
    </label>
    <g:field name="amount" value="${fieldValue(bean: transferInstance, field: 'amount')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: transferInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="transfer.description.label" default="Description" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="description" value="${transferInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: transferInstance, field: 'recipient', 'error')} required">
	<label for="recipient">
		<g:message code="transfer.recipient.label" default="Recipient" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="recipient" name="recipient.id" from="${tk.pimabank.User.find{ username != user.username }}" 
	optionKey="id" optionValue="username" required="" value="${transferInstance?.recipient?.id}" class="many-to-one"/>
</div>



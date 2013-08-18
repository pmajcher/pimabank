<%@ page import="tk.pimabank.Payment" %>


<div class="fieldcontain ${hasErrors(bean: paymentInstance, field: 'amount', 'error')} ">
    <label for="amount">
        <g:message code="payment.amount.label" default="Amount" />
        <span class="required-indicator">*</span>
        
    </label>
    <g:field name="amount" value="${fieldValue(bean: paymentInstance, field: 'amount')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: paymentInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="payment.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${paymentInstance?.description}"/>
</div>





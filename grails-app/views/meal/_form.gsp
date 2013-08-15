<%@ page import="tk.pimabank.Meal" %>



<div class="fieldcontain ${hasErrors(bean: mealInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="meal.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${mealInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: mealInstance, field: 'amount', 'error')} ">
	<label for="amount">
		<g:message code="meal.amount.label" default="Amount" />
		
	</label>
	<g:field name="amount" value="${fieldValue(bean: mealInstance, field: 'amount')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: mealInstance, field: 'agregator', 'error')} required">
	<label for="agregator">
		<g:message code="meal.agregator.label" default="Agregator" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="agregator" name="agregator.id" from="${tk.pimabank.MealAgregator.list()}" optionKey="id" required="" value="${mealInstance?.agregator?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: mealInstance, field: 'approved', 'error')} ">
	<label for="approved">
		<g:message code="meal.approved.label" default="Approved" />
		
	</label>
	<g:checkBox name="approved" value="${mealInstance?.approved}" />
</div>

<div class="fieldcontain ${hasErrors(bean: mealInstance, field: 'partOfMeal', 'error')} required">
	<label for="partOfMeal">
		<g:message code="meal.partOfMeal.label" default="Part Of Meal" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="partOfMeal" type="number" value="${mealInstance.partOfMeal}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: mealInstance, field: 'user', 'error')} required">
	<label for="user">
		<g:message code="meal.user.label" default="User" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="user" name="user.id" from="${tk.pimabank.User.list()}" optionKey="id" required="" value="${mealInstance?.user?.id}" class="many-to-one"/>
</div>


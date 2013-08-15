
<%@ page import="tk.pimabank.Meal" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'meal.label', default: 'Meal')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-meal" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-meal" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list meal">
			
				<g:if test="${mealInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="meal.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${mealInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${mealInstance?.amount}">
				<li class="fieldcontain">
					<span id="amount-label" class="property-label"><g:message code="meal.amount.label" default="Amount" /></span>
					
						<span class="property-value" aria-labelledby="amount-label"><g:fieldValue bean="${mealInstance}" field="amount"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${mealInstance?.agregator}">
				<li class="fieldcontain">
					<span id="agregator-label" class="property-label"><g:message code="meal.agregator.label" default="Agregator" /></span>
					
						<span class="property-value" aria-labelledby="agregator-label"><g:link controller="mealAgregator" action="show" id="${mealInstance?.agregator?.id}">${mealInstance?.agregator?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${mealInstance?.approved}">
				<li class="fieldcontain">
					<span id="approved-label" class="property-label"><g:message code="meal.approved.label" default="Approved" /></span>
					
						<span class="property-value" aria-labelledby="approved-label"><g:formatBoolean boolean="${mealInstance?.approved}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${mealInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="meal.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${mealInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${mealInstance?.partOfMeal}">
				<li class="fieldcontain">
					<span id="partOfMeal-label" class="property-label"><g:message code="meal.partOfMeal.label" default="Part Of Meal" /></span>
					
						<span class="property-value" aria-labelledby="partOfMeal-label"><g:fieldValue bean="${mealInstance}" field="partOfMeal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${mealInstance?.user}">
				<li class="fieldcontain">
					<span id="user-label" class="property-label"><g:message code="meal.user.label" default="User" /></span>
					
						<span class="property-value" aria-labelledby="user-label"><g:link controller="user" action="show" id="${mealInstance?.user?.id}">${mealInstance?.user?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${mealInstance?.id}" />
					<g:link class="edit" action="edit" id="${mealInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>

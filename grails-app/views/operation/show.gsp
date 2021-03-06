
<%@ page import="tk.pimabank.Operation" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'operation.label', default: 'Operation')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-operation" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-operation" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list operation">
			
				<g:if test="${operationInstance?.amount}">
				<li class="fieldcontain">
					<span id="amount-label" class="property-label"><g:message code="operation.amount.label" default="Amount" /></span>
					
						<span class="property-value" aria-labelledby="amount-label"><g:fieldValue bean="${operationInstance}" field="amount"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${operationInstance?.approved}">
				<li class="fieldcontain">
					<span id="approved-label" class="property-label"><g:message code="operation.approved.label" default="Approved" /></span>
					
						<span class="property-value" aria-labelledby="approved-label"><g:formatBoolean boolean="${operationInstance?.approved}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${operationInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="operation.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${operationInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${operationInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="operation.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${operationInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${operationInstance?.user}">
				<li class="fieldcontain">
					<span id="user-label" class="property-label"><g:message code="operation.user.label" default="User" /></span>
					
						<span class="property-value" aria-labelledby="user-label"><g:link controller="user" action="show" id="${operationInstance?.user?.id}">${operationInstance?.user?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${operationInstance?.id}" />
					<g:link class="edit" action="edit" id="${operationInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>

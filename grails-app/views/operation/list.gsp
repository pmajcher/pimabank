
<%@ page import="com.cloudfoundry.pimabank.Operation" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'operation.label', default: 'Operation')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-operation" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-operation" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="amount" title="${message(code: 'operation.amount.label', default: 'Amount')}" />
					
						<g:sortableColumn property="approved" title="${message(code: 'operation.approved.label', default: 'Approved')}" />
					
						<g:sortableColumn property="date" title="${message(code: 'operation.date.label', default: 'Date')}" />
					
						<th><g:message code="operation.user.label" default="User" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${operationInstanceList}" status="i" var="operationInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${operationInstance.id}">${fieldValue(bean: operationInstance, field: "amount")}</g:link></td>
					
						<td><g:formatBoolean boolean="${operationInstance.approved}" /></td>
					
						<td><g:formatDate date="${operationInstance.date}" /></td>
					
						<td>${fieldValue(bean: operationInstance, field: "user")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${operationInstanceTotal}" />
			</div>
		</div>
	</body>
</html>

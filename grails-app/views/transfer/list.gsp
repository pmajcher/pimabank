
<%@ page import="tk.pimabank.Transfer" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'transfer.label', default: 'Transfer')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-transfer" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-transfer" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="description" title="${message(code: 'transfer.description.label', default: 'Description')}" />
					
						<g:sortableColumn property="amount" title="${message(code: 'transfer.amount.label', default: 'Amount')}" />
					
						<g:sortableColumn property="approved" title="${message(code: 'transfer.approved.label', default: 'Approved')}" />
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'transfer.dateCreated.label', default: 'Date Created')}" />
					
						<th><g:message code="transfer.recipient.label" default="Recipient" /></th>
					
						<th><g:message code="transfer.user.label" default="User" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${transferInstanceList}" status="i" var="transferInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${transferInstance.id}">${fieldValue(bean: transferInstance, field: "description")}</g:link></td>
					
						<td>${fieldValue(bean: transferInstance, field: "amount")}</td>
					
						<td><g:formatBoolean boolean="${transferInstance.approved}" /></td>
					
						<td><g:formatDate date="${transferInstance.dateCreated}" /></td>
					
						<td>${fieldValue(bean: transferInstance, field: "recipient")}</td>
					
						<td>${fieldValue(bean: transferInstance, field: "user")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${transferInstanceTotal}" />
			</div>
		</div>
	</body>
</html>

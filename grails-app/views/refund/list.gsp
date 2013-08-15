
<%@ page import="tk.pimabank.Refund" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'refund.label', default: 'Refund')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-refund" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-refund" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="description" title="${message(code: 'refund.description.label', default: 'Description')}" />
					
						<g:sortableColumn property="amount" title="${message(code: 'refund.amount.label', default: 'Amount')}" />
					
						<g:sortableColumn property="approved" title="${message(code: 'refund.approved.label', default: 'Approved')}" />
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'refund.dateCreated.label', default: 'Date Created')}" />
					
						<th><g:message code="refund.user.label" default="User" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${refundInstanceList}" status="i" var="refundInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${refundInstance.id}">${fieldValue(bean: refundInstance, field: "description")}</g:link></td>
					
						<td>${fieldValue(bean: refundInstance, field: "amount")}</td>
					
						<td><g:formatBoolean boolean="${refundInstance.approved}" /></td>
					
						<td><g:formatDate date="${refundInstance.dateCreated}" /></td>
					
						<td>${fieldValue(bean: refundInstance, field: "user")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${refundInstanceTotal}" />
			</div>
		</div>
	</body>
</html>


<%@ page import="tk.pimabank.BugReport" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'bugReport.label', default: 'BugReport')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-bugReport" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-bugReport" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'bugReport.dateCreated.label', default: 'Date Created')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'bugReport.description.label', default: 'Description')}" />
					
						<g:sortableColumn property="done" title="${message(code: 'bugReport.done.label', default: 'Done')}" />
					
						<th><g:message code="bugReport.user.label" default="User" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${bugReportInstanceList}" status="i" var="bugReportInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${bugReportInstance.id}">${fieldValue(bean: bugReportInstance, field: "dateCreated")}</g:link></td>
					
						<td>${fieldValue(bean: bugReportInstance, field: "description")}</td>
					
						<td><g:formatBoolean boolean="${bugReportInstance.done}" /></td>
					
						<td>${fieldValue(bean: bugReportInstance, field: "user")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${bugReportInstanceTotal}" />
			</div>
		</div>
	</body>
</html>

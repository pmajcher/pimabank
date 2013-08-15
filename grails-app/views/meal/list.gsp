
<%@ page import="tk.pimabank.Meal" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'meal.label', default: 'Meal')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-meal" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-meal" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="description" title="${message(code: 'meal.description.label', default: 'Description')}" />
					
						<g:sortableColumn property="amount" title="${message(code: 'meal.amount.label', default: 'Amount')}" />
					
						<th><g:message code="meal.agregator.label" default="Agregator" /></th>
					
						<g:sortableColumn property="approved" title="${message(code: 'meal.approved.label', default: 'Approved')}" />
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'meal.dateCreated.label', default: 'Date Created')}" />
					
						<g:sortableColumn property="partOfMeal" title="${message(code: 'meal.partOfMeal.label', default: 'Part Of Meal')}" />
					
					    <g:sortableColumn property="user" title="${message(code: 'meal.user.label', default: 'User')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${mealInstanceList}" status="i" var="mealInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td>
						<g:link action="show" id="${mealInstance.id}">${fieldValue(bean: mealInstance, field: "description")}
					
						    <g:if test="mealInstance.agregator != null">
	                          ${mealInstance.agregator.mealName}
	                        </g:if>
	                        <g:else>
	                          ${fieldValue(bean: mealInstance, field: "description")}
	                        </g:else>
					    </g:link>
					    </td>
					
						<td>
						<g:if test="mealInstance.agregator != null">
						  ${(mealInstance.agregator.mealPrice * mealInstance.partOfMeal)/ 8 }
						</g:if>
						<g:else>
						  ${fieldValue(bean: mealInstance, field: "amount")}
						</g:else>
						
						
						</td>
					
						<td>${mealInstance.agregator.id}</td>
					
						<td><g:formatBoolean boolean="${mealInstance.approved}" /></td>
					
						<td><g:formatDate date="${mealInstance.dateCreated}" type="datetime" style="MEDIUM"/></td>
					
						<td>${fieldValue(bean: mealInstance, field: "partOfMeal")}</td>
						
						<td>${mealInstance.user.username}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${mealInstanceTotal}" />
			</div>
		</div>
	</body>
</html>

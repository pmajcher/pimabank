<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
	 <meta name='layout' content='main'/>
	     <g:javascript library='jquery' />
     <style type="text/css">
     </style>
     <g:javascript>
     </g:javascript>

	</head>
	<body>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/account')}"><g:message code="default.myaccount.label"/></a></li>
            </ul>
        </div>

	    <h2>Witaj ${principal.username}</h2>
		<h3>Historia</h3>
		
		<a href="#list-operation" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

		<div id="list-operation" class="content scaffold-list" role="main">

			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
						<th><g:message code="operation.type.label" default="Operation" /></th>                    
						<g:sortableColumn property="date" title="${message(code: 'operation.date.label', default: 'Date')}" />
						<g:sortableColumn property="amount" title="${message(code: 'operation.amount.label', default: 'Amount')}" />
						<g:sortableColumn property="description" title="${message(code: 'operation.description.label', default: 'Description')}" />
						<g:sortableColumn property="approved" title="${message(code: 'operation.approved.label', default: 'Approved')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${operations}" status="i" var="operationInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						
						<td>${operationInstance.operationType}</td>
						<td><g:formatDate date="${operationInstance.date}" format="yyyy-mm-dd HH:mm"/></td>
						<td>${fieldValue(bean: operationInstance, field: "amount")}</td>
						<td>${operationInstance.description}</td>
						<td>${operationInstance.approved}</td>
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

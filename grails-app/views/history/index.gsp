<script>
redrawChart("home/ajaxTest", 
		    "Stan konta",  
		    "Historyczny stan konta - ostatnie 20 transkacji",
		    "history-chart");
</script>


<h3>Historia</h3>
<h1 style="padding-bottom: 20px;">Wykres osatnich operacji</h1>
<div id="history-chart"></div>

<a href="#list-operation" class="skip" tabindex="-1">
    <g:message code="default.link.skip.label" default="Skip to content&hellip;"/>
</a>

<h1 style="padding-bottom: 20px;">Lista operacji</h1>

<div id="list-operation" class="content scaffold-list" role="main">

	<g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
	</g:if>
	<table>
		<thead>
			<tr>
				<th width="7%"><g:message code="operation.type.label" default="Operation" /></th>                    
			    <th width="25%"><g:message code="${message(code: 'operation.createdDate.label', default: 'Created Date')}" /></th>
                <th width="15%" style="text-align: right; padding-right: 15px;"><g:message code="${message(code: 'operation.amount.label', default: 'Amount')}" /></th>
                <th><g:message code="${message(code: 'operation.description.label', default: 'Description')}" /></th>
                <th width="15%" style="text-align: center"><g:message code="${message(code: 'operation.state.label', default: 'State')}" /></th>					
			</tr>
		</thead>
		<tbody>
		<g:each in="${operations}" status="i" var="operationInstance">
			<tr class="${(i % 2) == 0 ? 'even' : 'odd'}" >
				<td style="text-align: center;">
				    <g:if test="${operationInstance.operationType.name() == 'PAYMENT'}">
				        <g:img dir="images" file="payment-icon.png" width="25" height="25"
				        alt="Wpłata" title="Wpłata"/>
				    </g:if>
				    <g:elseif test="${operationInstance.operationType.name() == 'PURCHASE'}">
				        <g:img dir="images" file="meal-icon.png" width="25" height="25"
				        alt="Zamówienie" title="Zamówienie"/>
				    </g:elseif>
                    <g:elseif test="${operationInstance.operationType.name() == 'LOAN'}">
                        <g:img dir="images" file="debit-icon.png" width="25" height="25"
                        alt="Wypłata" title="Wypłata"/>
                    </g:elseif>
                    <g:elseif test="${operationInstance.operationType.name() == 'REFUND'}">
                        <g:img dir="images" file="refund-icon.png" width="25" height="25"
                        alt="Refund" title="Zwrot"/>
                    </g:elseif>
				</td>
				<td><g:formatDate date="${operationInstance.dateCreated}" type="datetime" style="LONG" timeStyle="SHORT"/>
				</td>
				<td style="padding-right: 15px; text-align: right;">
				    <g:formatNumber number="${operationInstance.amount}" type="currency" 
                              minFractionDigits="2" currencyCode="PLN" />  
				</td>
				<td>
				  <g:if test="operationInstance.description">
				      ${operationInstance.description}    
				  </g:if>
				  <g:else>
                      ${operationInstance.agreagator.mealName} 			     
				  </g:else>
				</td>
				<td style="text-align: center; ${!operationInstance.approved ? 'color: red;' : ''}">

				    <g:if test="${operationInstance.approved == true}">
				        ${message(code: 'operation.approved', default: 'Approved')}
				    </g:if>
				    <g:else>
				        ${message(code: 'operation.notapproved', default: 'Not approved')}
				    </g:else>
			    </td>
			</tr>
		</g:each>
		</tbody>
	</table>
</div>


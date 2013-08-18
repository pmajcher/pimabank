<h1>Przelewy przychodzące</h1>


<table>
    <thead>
        <tr>
            <th><g:message code="${message(code: 'operation.description.label', default: 'Description')}" /></th>
    
            <th width="25%">Od kogo</th>    
            <th width="15%" style="text-align: right; padding-right: 15px;"><g:message code="${message(code: 'operation.amount.label', default: 'Amount')}" /></th>
            <th width="25%"><g:message code="${message(code: 'operation.createdDate.label', default: 'Created Date')}" /></th>
            <th width="10%">Zatwierdź</th>
            
        </tr>
    </thead>
    <tbody>
        <g:each var="transfer" in="${transfers}" status="i">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}" >
                <td>${transfer.description}</td>
                <td>${transfer.user.username}</td>
                <td style="padding-right: 15px; text-align: right;">
                    <g:formatNumber number="${transfer.amount}" type="currency" 
                              minFractionDigits="2" currencyCode="PLN" />  
                </td>
                <td><g:formatDate date="${transfer.dateCreated}" type="datetime" style="LONG" timeStyle="SHORT"/>
                <td>
                <div class="approve-button"
                                onclick="${remoteFunction(
                                                  action: 'approveTransfer',
                                                  update: 'approveTransferAjaxResult',
                                                  onComplete:'reloadButtons();  updateAccountState();',
                                                  id: transfer.id )}; return false;">
                                <button class="approve">Zatwierdz</button>
                            </div>
                
                </td>
            </tr>
        </g:each>
    </tbody>
</table>

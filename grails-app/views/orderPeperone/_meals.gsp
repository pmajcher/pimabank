<table>
    <thead>
    <tr>
        <th width="7%">Kto</th>
        <th width="68%">Co</th>
        <th width="15%">Cena</th>
        <th width="10%"></th>
    </tr>
    </thead>
    <tbody>
    <g:each var="meal" in="${order.meals}" status="i">
    <tr id=${meal.id} class="one-meal">
        <td><b>${meal.user.username}</b></td>
        <td>${meal.description}</td>
        <td>
        	<g:formatNumber number="${meal.amount}" type="currency" currencyCode="PLN" />
        </td>
        <td>
            <g:if test="${order.open == true && meal.user.username == principal.username}">
                <div class="remove-button"
                    onclick="${remoteFunction(action: 'removeMeal',
                                              update: 'resultsAjax',
						 					  onComplete:'reloadButtons()',
                                              id: meal.id )}; return false;">
                     <button class="delete">Usuń</button>
               	</div>
            </g:if>
        </td>
    </tr>
    </g:each>
    </tbody>
</table>




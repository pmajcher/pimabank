<style>
.getPartsOfMeal{
    color: green;
    font-size: 12px;
    font-weight: bold;
    cursor: pointer;
}

</style>

<table>
	<thead>
		<tr>
			<th width="50%">Co</th>
			<th width="25%">Kto</th>	
			<th width="15%">Cena</th>
			<th width="10%"></th>
		</tr>
	</thead>
	<tbody>
		<g:each var="mealAgregator" in="${order.mealAgregators}" status="i">
    		<g:each var="meal" in="${mealAgregator.meals}" status="j">	
	       		<tr id=${mealAgregator.id} class="one-meal">
				    <td>
				        <g:if test="${j ==0 }">
                            ${mealAgregator.mealName} 
                            <g:if test="${mealAgregator.getFreeParts() > 0}">
                                <span class="getPartsOfMeal">[dopisz się]</span>
                                <div class="getPartsOfMealBox">
	                                <g:formRemote 
	                                    name="myForm" 
	                                    update="resultsAjax"
	                                    url="[controller: 'orderPeperone', action: 'orderPartOfMeal']"
	                                    onComplete="onCompleteOrder()">
	                                <div style="padding-top: 10px;">
                                        <div class="orderPartLabel">Jaką część chcesz zamówić?:</div>
                                        <div class="orderPartInfo">1/8</div>

	                                    <input name="agregatorId" value="${mealAgregator.id}" type="hidden" /> 
	                                    <input name="partOfMeal" class="partOfMeal" type="hidden" value="1"/>
	                                    
	                                    <div class="slider-wrapper">
	                                       <div class="partOfMealSlider"></div>
                                           <div class="slider-axis">
					                          <div class="left-slider">1/8</div>
						                      <div class="right-slider">1</div>
						                   </div>
						                </div>
	                                    
	                                    
	                                    <div class="startSlider" >${8- mealAgregator.getFreeParts()}</div>
	                               	</div>       
	                                 <div class="order-meal-button">
						                <input value="Submit" type="submit">  
						            </div>    
	                            </g:formRemote> 
	                        </div> 
	                    </g:if>
	                </g:if>
	                </td>				
					<td>
					    <b>${meal.user.username}
	                    <g:if test="${mealAgregator.meals.size() > 1 || mealAgregator.getFreeParts() > 0}">
	                        - ${meal.partOfMeal}/8 
	                    </g:if>
					    </b>
					</td>
					<td>
	                     <g:formatNumber number="${meal.partOfMeal/8 * mealAgregator.mealPrice}" type="currency"
	                        minFractionDigits="2" currencyCode="PLN" />
	                </td>
					<td>
	                    <g:if test="${false && order.state.name() == 'OPEN' && meal.user.username == principal.username}">
	                        <div class="remove-button"
	                            onclick="${remoteFunction(
									              action: 'removeMeal',
	                                              update: 'resultsAjax',
	                                              onComplete:'reloadButtons();  updateAccountState();',
	                                              id: meal.id )}; return false;">
	                            <button class="delete">Usuń</button>
	                        </div>
	                    </g:if>
	
					</td>
				</tr>
			</g:each>
		</g:each>
	</tbody>
</table>





<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
<meta name='layout' content='main' />
<r:require module="jquery-ui" />

<g:javascript>
$(function() {
	reloadButtons();
});

function reloadButtons(){
	$( "button.order" ).button()
		.click(function( event ) {
		});
	$( "button.delete" ).button()
		.click(function( event ) {
		});
}
        
</g:javascript>

</head>
<body>

	<div class="nav" role="navigation">
		<ul>
			<li><a class="home" href="${createLink(uri: '/account')}">
				<g:message code="default.myaccount.label" /></a>
			</li>
		</ul>
	</div>


	<h1>Zamówienie 
		<g:if test="${order.open==false}">	
		zamknięte
		</g:if>
	</h1>
    <div id="resultsAjax">
         <g:render template="meals" model="[order: order]" />
    </div>


	<g:if test="${order.open==true}">	

        <h1>Dodaj danie ręcznie</h1>

        <g:formRemote name="myForm" update="resultsAjax"
                      url="[controller: 'orderPeperone', action: 'orderMealManually']"
                      onComplete="document.getElementById('myForm').reset(); reloadButtons();">
                       <div>
                           <div>
                            <label for="what">Co:</label>
                           <input name="what" maxlength="50" value="" id="what" type="text">
                             <label for="price">Cena:</label>
                           <input name="price" class="price" maxlength="50" value="" id="price" type="text">
                            <input value="Submit" type="submit">
                       </div>
        </g:formRemote>

        <h1>Dania dnia w Peperone</h1>
        <table>
        <thead>
            <tr>
                <th width="75%">Nazwa</th>
                <th width="15%">Cena</th>
                <th width="10%"></th>
            </tr>
        <thead>
        <tbody>
            <g:each var="meal" in="${meals}">
            <tr>
                <td>${meal.description}</td>
                <td>
                	 <g:formatNumber number="${meal.amount}" type="currency" currencyCode="PLN" />
                </td>
                <td>
                <div onclick="${remoteFunction(action: 'orderMealOfDay',
                                       update: [success: 'resultsAjax', failure: 'ohno'],
									   onComplete:'reloadButtons()',
                                       id: meal.description)}; return false;">
                <button class="order" name="${meal.description}">Zamów</button>
                </div></td>
            </tr>
            </g:each>
           </tbody>
        </table>
        
        </g:if>
	</body>
</html>

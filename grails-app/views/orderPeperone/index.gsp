 <script>
 function onCompleteOrder() {
	 hideWaitPage();
     document.getElementById('myForm').reset();
     reloadButtons();
     reloadAllSliders();
     updateAccountState();
     $('#pizza-box').hide(500);
}
function onLoading(){
     showWaitPage();
}
 
 $(function() {
	 $( "#slider" ).slider({
		    min: 1,
		    max: 8,
		    range: "min",
		    step: 1,
		    slide: function( event, ui ) {
		    	$( "#orderPartView" ).html(ui.value +"/8");
		    	$( "#partOfMeal").val(ui.value);
		    }
		 });

	    $( "#isShared").on("click", function(){
	    	if (this.checked){
	    		 $('#pizza-box').show(500);
	    	}else{
	    		 $('#pizza-box').hide(500);
	    	}
	       
		});

	      
	    /*
	    $(" .partOfMealSlider").slider({
            min: 1,
            max: 8,
            value: $(this).parent().find(".startSlider").html(),
            range: "min",
            step: 1,
            slide: function( event, ui ) {
            	if(ui.value < $(this).parent().find(".startSlider").html()){
                    return false;
                }
                
                $(this).parent().find(".amount").val(ui.value +"/8");
             //   $( "#amount" ).val(ui.value +"/8");
                $( "#partOfMeal").val(ui.value);
            }
         });
*/

       reloadAllSliders();

		$(document.body).on("click",".getPartsOfMeal", function(){
            $(this).parent().find(".getPartsOfMealBox").show("slow");
			$(".getPartsOfMealBox").not( $(this).parent().find(".getPartsOfMealBox")).hide("slow");
		//	alert("aa");

	    });
 });

 function reloadAllSliders(){

     $(" .partOfMealSlider").each(function(){
         var alreadyOrdered = parseInt($(this).parent().parent().find(".startSlider").html());

         $(this).slider({
             min: 1,
             max: 8,
             value: alreadyOrdered + 1,
             range: "min",
             step: 1,
             slide: function( event, ui ) {
                 if(ui.value <= alreadyOrdered){
                     return false;
                 } 
                 $(this).parent().parent().find(".orderPartInfo").html(ui.value - alreadyOrdered +"/8");
                 $(this).parent().parent().find(".partOfMeal").val(ui.value - alreadyOrdered);
             }
             })
     });
 }
 
 
 </script>
 
 <style>
 
 
 
 </style>
 
<g:if test="${order != null}">
    <h3 style="padding-bottom: 20px;">Zamówienie 
	   <g:if test="${order.state.name() !=  'OPEN'}">	
		zamknięte
		</g:if>
	</h3>

	<g:if test="${order.state.name() == 'OPEN'}">	
       <h3 style="padding-bottom: 20px;">Dodaj pozycję</h3>
       <g:formRemote name="myForm" 
                     update="resultsAjax"
                     url="[controller: 'orderPeperone', action: 'orderMealManually']"
                     onComplete="onCompleteOrder()"
                     onLoading="onLoading()">
       <div>
           <label for="what">Co:</label>
           <input name="what" maxlength="150" value="" id="what" type="text">
           <label for="price">Cena:</label>
           <input name="price" class="price" maxlength="10" value="" id="price" type="text"><br>
           <br>
           <label for="isShared">Czy chcesz zamówić tylko część pozycji?:</label>
           <input name="isShared" id= "isShared" type="checkbox">
           <input name="partOfMeal" id= "partOfMeal" value="1" type="hidden">         
           <div id="pizza-box">
	          <div id="orderPartLabel">Jaką część chcesz zamówić?:</div>

	           <div id="orderPartView">1/8</div>
	           
	           <div class="slider-wrapper">
	               <div id="slider"></div>
	               <div class="slider-axis">
	                   <div class="left-slider">1/8</div>
	                    <div class="right-slider">1</div>
	               </div>
	           </div>     
            </div>
            <div class="order-meal-button">
			    <input value="Submit" type="submit">  
			</div>       
        </div>
    </g:formRemote>
</g:if>

    <div id="resultsAjax" style="padding-top: 30px;]">
         <g:render template="meals" model="[order: order]" />
    </div>


<%--
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
        --%>
        </g:if>
        
        <g:else>
        <h1>Zamówienie jeszcze nie utworzone</h1>
        </g:else>
        
        

 <script type="text/javascript">

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
        
</script> 

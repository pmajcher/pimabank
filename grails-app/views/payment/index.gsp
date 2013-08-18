<script type="text/javascript">
function onSuccessPayment() {
	hideWaitPage();
    document.getElementById('paymentForm').reset();
//  alert("");
    updateAccountState();
}


function onSuccessPayment(response) {
    hideWaitPage();
    document.getElementById('paymentForm').reset();
    $(".errors").slideUp()
         .html("");
    $(".message").html(response)
        .slideDown()
        .delay(3000)
        .slideUp();
    
    updateAccountState();
}

function onFailurePayment(data) {
    hideWaitPage();
 //   alert("size " + data.responseText);
    var JSONObj = $.parseJSON(data.responseText);
    console.log(JSONObj.text);
    var realArray = $.makeArray( JSONObj.text );
 //   alert("realArray: " + realArray);
    $(".errors").html("");
 
    jQuery.each(realArray, function() {
         $(".errors").append("<li>"+this+"</li>");
        });
   
    $(".errors").slideDown(); 
}

</script>

<%--<g:formRemote name="paymentForm" update="success"
	url="[controller: 'payment', action: 'pay']"
	onComplete="onCompletePayment()"
	onLoading="onLoading()"
	before="checkiIfUserIsLogged()">
	<div>
		<div>
			<label for="amount">Kwota:</label> <input name="amount"
				maxlength="50" value="" id="amount" type="text"> <label
				for="price">Opis:</label> <input name="desc" class="desc"
				maxlength="50" value="" id="desc" type="text"> <input
				value="Submit" type="submit">
		</div>
	</div>
</g:formRemote>



--%>

 <h1><g:message code="payment.create" /></h1>
 
        <div id="create-payment" class="content scaffold-create" role="main">
            
            <ul class='errors' role='alert' hidden="true"></ul>
            <div class="message" role="status" hidden="true"></div>

            <g:formRemote name="paymentForm" update="success"
					    url="[controller: 'payment', action: 'pay']"
					    onSuccess="onSuccessPayment(data)"
					    onFailure="onFailurePayment(XMLHttpRequest)"
					    onLoading="showWaitPage()"
					    before="checkiIfUserIsLogged()">
                <fieldset class="form">
                    <g:render template="ajaxForm"/>
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </fieldset>
            </g:formRemote>
        </div>





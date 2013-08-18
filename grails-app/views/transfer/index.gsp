<%@ page import="tk.pimabank.Transfer" %>


<script type="text/javascript">
function onSuccessTransfer(response) {
    hideWaitPage();
    document.getElementById('transferForm').reset();
    $(".errors").slideUp()
         .html("");
    $(".message").html(response)
        .slideDown()
        .delay(3000)
        .slideUp();
    
    updateAccountState();
}

function onFailureTransfer(data) {
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

$(function() {
    reloadButtons();
});

function reloadButtons(){
    $( "button.approve" ).button()
        .click(function( event ) {
        });
}
        
</script> 

 
    <body>
    
     
 <h1><g:message code="transfer.create" /></h1>
           
        <div id="create-transfer" class="content scaffold-create" role="main">
            
            <ul class='errors' role='alert' hidden="true"></ul>
            <div class="message" role="status" hidden="true"></div>
            
            <g:formRemote id="transferForm"
                          name ="transferForm" 
                          url="[controller: 'transfer', action: 'ajaxSave']"
                          onSuccess="onSuccessTransfer(data)"
                          onFailure="onFailureTransfer(XMLHttpRequest);"
						  onLoading="showWaitPage()"
					      before="checkiIfUserIsLogged()" >
                <fieldset class="form">
                    <g:render template="ajaxForm"/>
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </fieldset>
            </g:formRemote>
        </div>
        
         <div id="approveTransferAjaxResult" style="padding-top: 30px;]">
            <g:render template="incommingTransfers" model="[transfers: transfers]" />
         </div>
    </body>


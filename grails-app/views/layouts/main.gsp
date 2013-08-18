<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="PimaBank"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">


<script type="text/javascript" src="${resource(dir:'js',file:'jquery-1.9.1.js')}"></script>
<script type="text/javascript" src="${resource(dir:'js',file:'jquery-ui-1.10.3.custom.js')}"></script>
<link rel="stylesheet" href="${resource(dir:'css',file:'custom-themes/black-tie/jquery-ui-1.10.3.custom.css')}" />
<link rel="stylesheet" href="${resource(dir:'css',file:'custom-themes/black-tie/jquery.ui.tabs.css')}" />


<script type="text/javascript" src="${resource(dir:'js/highcharts-3.0.2/js',file:'highcharts.js')}"></script>
<script type="text/javascript" src="${resource(dir:'js/highcharts-3.0.2/js/modules',file:'exporting.js')}"></script>

<style type="text/css">
#overlayDiv {
    float: right;
}


</style>
<script type="text/javascript">


$(function() {


    var description = $( "#description" );

	$('#report-bug').button()
	.click(function(){
		 checkiIfUserIsLogged();
		 $( "#dialog-modal" ).dialog( "open" );
	 });

	
    $( "#dialog-modal" ).dialog({
        height: 440,
        width: 350,
        modal: true,
        autoOpen: false,
        buttons: {
            "Wyślij": function() {
                 $.ajax({
                     url: "${g.createLink(controller:'bugReport',action:'createAjax')}",
                     data: {description: description.val()}
                     }).done(function(response) {
                       alert("Zgłoszono błąd. Dziękujemy:)");
                       $( "#dialog-modal"  ).dialog( "close" );
                   //    $( "#description" ).val('');
                     });
                },
        	 "Anuluj": function() {
        		 $( this ).dialog( "close" );
        		 } 
        }
        });
});

function showWaitPage(){
	  $('#loading').show();
}

function hideWaitPage(){
	  $('#loading').hide();
}


function checkiIfUserIsLogged(){
	 $.ajax({
         url: "${g.createLink(controller:'login',action:'isLoggedIn')}",
         }).done(function(response) {
       	    if(response.isLogged != true){
           	    alert("Sesja użytkownik wygasła");
       	    	window.location.replace("login");
       	    }
         });
    }


</script>



		<g:layoutHead/>
		<r:layoutResources />
	</head>
	<body>
<div id="loading">
  <g:img id="loading-image" dir="images" file="ajax_loader.gif"alt="Loading..."/>
</div>	
	
	
	<div id="dialog-modal" title="Zgłoszenie błędu">
<p>Zgłaszając błąd podaj jak najwięcej szczegółów. Dzięki temu poprawa błędu będzie szybsza.</p>

 <form>
<fieldset>
<label for="description">Opis błędu</label>
<textarea rows="4" cols="50" name="description" id="description" class="text ui-widget-content ui-corner-all">
</textarea>

</fieldset>
</form>

</div>
	
		<%--<div id="grailsLogo" role="banner"><a href="http://grails.org"><img src="${resource(dir: 'images', file: 'grails_logo.png')}" alt="Grails"/></a></div>--%>
		<div style="height: 90px; width:  100%; background: black;">
		  <div style="padding: 10px; color: white; font-size: 40px; width: 100px; float: left;">PimaBank</div>
		  

 <sec:ifLoggedIn>
		            <div style="width: 30px; text-align: right; float: right; padding-top: 10px;
          padding-right: 30px; font-size: 14px; ">
         
          
          <g:link style=" text-decoration: none; color: white;" controller="logout">Wyloguj</g:link>
         
         

          </div>
		  <div style="width: 250px; float: right; padding-top: 20px;">
		  <div id='report-bug'>
		  <div style="float: left; width: 50px;">
			<img src="${resource(dir: 'images', file: 'report-bug-icon.png')}" width="44" height="44" />
		  </div>
			<div style="float: left;width: 150px; font-size: 18px; font-weight: bold; padding-top: 7px;">Zgłoś błąd</div>
			</div>
		  
		  </div>
 </sec:ifLoggedIn>		  

		</div>
		<g:layoutBody/>
		<div class="footer" role="contentinfo"></div>
		<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
		<g:javascript library="application"/>
		<r:layoutResources />
	</body>
</html>

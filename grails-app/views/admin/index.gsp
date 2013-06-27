<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	
<head>
<meta name='layout' content='main'/>
<r:require module="jquery-ui"/>

<g:javascript>

$(function() {
	reloadButtons();
});


function reloadButtons(){
	$( "button.trig" ).button()
		.click(function( event ) {	
		$.ajax({
  			url:"${request.contextPath}/admin/trigOrder"
			})
			.done(function(data) {
			     if(data=="true"){
			        document.getElementById("trig").children[0].innerHTML = "Zamknij zamówienie"; 
			     }else{
			        document.getElementById("trig").children[0].innerHTML = "Otwórz zamówienie"; 
			     }
			});
		});
}
        
        
</g:javascript>



<style type="text/css">
</style>
</head>
<body>
<h2>
		Witaj admin:
		${user.username}
	</h2>


	<div id="content">
		<g:link controller="user">Users</g:link>
		<g:link controller="operation">Operations</g:link>
		
		<div>	
			<button class="trig" id="trig">
				<g:if test="${order.open == true}">
					Zamknij zamówienie
				</g:if>
				<g:else>
					Otwórz zamówienie
				</g:else>			
			</button>
		</div>
	</div>
</body>
</html>

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
	var orderState = $("#orderState").html();
	switch(orderState){
	   case "OPEN":
		   $('#openOrder').button("disable");
	       $('#closeOrder').button("enable");
	       $('#arrivedOrder').button("disable");
		   break;
	   case "CLOSE":
           $('#openOrder').button("enable");
           $('#closeOrder').button("disable");
           $('#arrivedOrder').button("enable");
           break;
       case "ARRIVED":
           $('#openOrder').button("disable");
           $('#closeOrder').button("disable");
           $('#arrivedOrder').button("disable");
           break;
	}
});



    $( "button#createEmail ").button()
        .click(function( event ) { 
        $.ajax({
            url:"${request.contextPath}/admin/createEmail"
            })
            .done(function(data) {
                alert(data);
            });
    });

    $( "button#closeOrder ").button()
        .click(function( event ) { 
        $.ajax({
            url:"${request.contextPath}/admin/closeOrder"
            })
            .done(function(data) {
                 $('#closeOrder').button("disable");
                 $('#openOrder').button("enable");
                 $('#arrivedOrder').button("enable");
            });
    });
    $( "button#openOrder ").button()
        .click(function( event ) { 
        $.ajax({
            url:"${request.contextPath}/admin/openOrder"
            })
            .done(function(data) {
                 $('#closeOrder').button("enable");
                 $('#openOrder').button("disable");
                 $('#arrivedOrder').button("disable");
            });
    });
    $( "button#arrivedOrder ").button()
        .click(function( event ) { 
        $.ajax({
            url:"${request.contextPath}/admin/arrivedOrder"
            })
            .done(function(data) {
                $('#arrivedOrder').button("disable");
                $('#openOrder').button("disable");
            });
    });
    


        
        
</g:javascript>



<style type="text/css">
</style>
</head>
<body>
<h2>
		Witaj admin: ${user.username}
	</h2>


	<div id="content">
		<g:link controller="user">Users</g:link>
		<g:link controller="operation">Operations</g:link>
		<g:link controller="mealOrder" action="list">Orders</g:link>
		
		
		
		<div>	
		  <g:if test="${order != null}">
		      <div id="orderState">${order.state}</div>
		
		
            <button id="closeOrder">
                Zamknij zamówienie
            </button>
            <button id="openOrder">
                Otwórz zamówienie
            </button>
            <button id="arrivedOrder">
                Zamówienie dostarczone
            </button>
            <button id=createEmail>
                Utwórz email
            </button>
            

			</g:if>
			<g:else>
			     Jeszcze nie ma zamówienia
			     <g:link controller="admin" action="createOrder">Utwórz zamówienie</g:link>
			</g:else>
			
			 <table>
                <thead>
                    <tr>
                        <th>Users</th>
                        <th>Account state</th>
                        <th>Not approved amount</th>
                    </tr>
                </thead>
                <tbody>
                  <tr>
                        
                        <td><b>Razem</b></td>
                        <td>${totalAmount}</td>
                        <td>${totalNotApproved}</td>
                    </tr>
                
                <g:each in="${users}" status="i" var="user">
                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                        
                        <td>${user.username}</td>
                        <td>${user.getAccountState()[0]}</td>
                        <td>${user.getAccountState()[1]}</td>
                    </tr>
                </g:each>
                </tbody>
            </table>
			
		</div>
	</div>
</body>
</html>

<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	
<head>
<meta name='layout' content='main'/>
<r:require module="jquery-ui"/>


<style type="text/css">
.big-button {
	height: 200px;
	width: 180px;
	float: left;
	margin: 20px;
	cursor: hand;
	cursor: pointer;
	display: table-cell;
	vertical-align: middle
}

#content {
	width: 900px;
	height: 400px;
	overflow: hidden;
}

img {
	width: 150px;
	display: block;
	margin-left: auto;
	margin-right: auto;
}

label,input {
	display: block;
}

input.text {
	margin-bottom: 12px;
	width: 95%;
	padding: .4em;
}

fieldset {
	padding: 0;
	border: 0;
	margin-top: 25px;
}

h1 {
	font-size: 1.2em;
	margin: .6em 0;
}

div#users-contain {
	width: 350px;
	margin: 20px 0;
}

div#users-contain table {
	margin: 1em 0;
	border-collapse: collapse;
	width: 100%;
}

div#users-contain table td,div#users-contain table th {
	border: 1px solid #eee;
	padding: .6em 10px;
	text-align: left;
}

.ui-dialog .ui-state-error {
	padding: .3em;
}

.validateTips {
	border: 1px solid transparent;
	padding: 0.3em;
}
</style>
<g:javascript>
var current_h = null;
var current_w = null;

$('img').hover(
    function(){
        current_h = $(this, 'img')[0].height;
        current_w = $(this, 'img')[0].width;
        $(this).stop(true, false).animate({width: (current_w * 1.1), height: (current_h * 1.1)}, 100);
    },
    function(){
        $(this).stop(true, false).animate({width: current_w + 'px', height: current_h + 'px'}, 100);
    }
);

$(function() {
	var amount =  $( "#amount" ),
	desc = $( "#desc" ),
	allFields = $( [] ).add( amount ).add( desc ) ;

	$( "#loan-dialog-form" ).dialog({
		autoOpen: false,
		height: 400,
		width: 450,
		modal: true,
		buttons: {
			"Weź pożyczkę": function() {
				$.ajax({
    				url:"${request.contextPath}/loan/getLoan",
					data : {amount : amount.val(),
							desc : desc.val()}
					})
					.done(function(data) {
						$("#loan-dialog-form").dialog("close");
					});
				},
			Cancel : function() {
				$(this).dialog("close");
			}
			},
		close : function() {
			allFields.val("");
		}
	});
	$("#get-loan").click(function() {
		$("#loan-dialog-form").dialog("open");
	});
	
	
	var pay_amount =  $( "#pay_amount" ),
	pay_desc = $( "#pay_desc" ),
	pay_allFields = $( [] ).add( pay_amount ).add( pay_desc ) ;
	
	
	$( "#payment-dialog-form" ).dialog({
		autoOpen: false,
		height: 400,
		width: 450,
		modal: true,
		buttons: {
			"Wpłata": function() {
				$.ajax({
    				url:"${request.contextPath}/account/pay",
					data : {amount : pay_amount.val(),
							desc : pay_desc.val()}
					})
					.done(function(data) {
						$("#payment-dialog-form").dialog("close");
					});
				},
			Cancel : function() {
				$(this).dialog("close");
			}
			},
		close : function() {
			pay_allFields.val("");
		}
	});
	
	$("#pay").click(function() {
		$("#payment-dialog-form").dialog("open");
	});
	
	
});
</g:javascript>

</head>
<body>
	<div id="loan-dialog-form" title="Weź pożyczkę">
		<p class="validateTips">Wypełnij wszystkie pola.</p>
		<form>
			<fieldset>
				<label for="name">Kwota</label> 
				<input type="text" name="amount" id="amount" class="text ui-widget-content ui-corner-all" /> 
				<label for="email">Opis</label> 
				<input type="text" name="desc" id="desc" value="" class="text ui-widget-content ui-corner-all" />
			</fieldset>
		</form>
	</div>
	
	<div id="payment-dialog-form" title="Wpłata">
		<p class="validateTips">Wypełnij wszystkie pola.</p>
		<form>
			<fieldset>
				<label for="name">Kwota</label> 
				<input type="text" name="pay_amount" id="pay_amount" class="text ui-widget-content ui-corner-all" /> 
				<label for="email">Opis</label> 
				<input type="text" name="pay_desc" id="pay_desc" value="" class="text ui-widget-content ui-corner-all" />
			</fieldset>
		</form>
	</div>
	
	<h2>
		Witaj
		${user.username}
	</h2>
	<h3>
		Stan konta:
		${amount}
	</h3>


	<div id="content">
		<g:link controller="orderPeperone">
			<div class="big-button">
				<g:if test="${isOrderOpen==true}">	
					<img src="${resource(dir: 'images', file: 'peperone-logo.png')}"
					alt="Zamów obiad z peperone" />
				</g:if>
				<g:else>
					<img src="${resource(dir: 'images', file: 'peperone-logo-close.png')}"
					alt="Zamówienie zamknięte" />
				</g:else>

			</div>

		</g:link>

		<div class="big-button" id="get-loan">
			<img src="${resource(dir: 'images', file: 'loan-cartoon.png')}"
				alt="Pożyczka" />
		</div>

		<div class="big-button" id="pay">
			<img id="image" src="${resource(dir: 'images', file: 'payment.jpg')}"
				alt="Wpłata" />
		</div>
		<g:link controller="history">
			<div class="big-button">
				<img id="image"
					src="${resource(dir: 'images', file: 'history.png')}"
					alt="Historia" />
			</div>
		</g:link>

	</div>
</body>
</html>

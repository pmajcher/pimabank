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
</head>
<body>
<h2>
		Witaj admin:
		${user.username}
	</h2>


	<div id="content">
		<g:link controller="orderPeperone">
			<div class="big-button">
				<img src="${resource(dir: 'images', file: 'peperone-logo.png')}"
					alt="Uzytkownicy" />
			</div>
		</g:link>

		<div class="big-button" id="get-loan">
			<img src="${resource(dir: 'images', file: 'loan-cartoon.png')}"
				alt="Operacje" />
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

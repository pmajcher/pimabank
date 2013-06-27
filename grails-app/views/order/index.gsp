<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>

	</head>
	<body>
		<h>Order Index <%= info %></h>

<table>
<tr>
    <td>Nazwa</td>
    <td>Cena</td>
    <td>Zamow</td>
</tr>
		<g:each var="meal" in="${meals}">
        <tr>
            <td>${meal.name}</td>
            <td>${meal.amount}</td>
            <td>Button</td>
        </tr>
        </g:each>

</table>

    <a style="display: block; height: 100%; width: 100%; text-decoration: none" href="/pimabank/orderPeperone">
        <div style="background: orange; height: 200px; width: 200px">Zamów obiad z peperone</div>
    </a>
    <div style="background: green; height: 200px; width: 200px">Kup coś innego</div>
    <div style="background: blue; height: 200px; width: 200px">Weź pożyczkę</div>
    <div style="background: red; height: 200px; width: 200px">Spłać pożyczkę</div>


	</body>
</html>

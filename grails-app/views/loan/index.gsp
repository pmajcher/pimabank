<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>

        <r:require module="jquery-ui"/>
          <g:javascript>

            $(document).ready(function()
            {
              $("#datepicker").datepicker({dateFormat: 'yy/mm/dd'});
            })
          </g:javascript>
         <meta name='layout' content='main'/>


	</head>
	<body>

	<div>
          <p> Between <input type="text" id="datepicker"> </p>
        </div>


        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/account')}"><g:message code="default.myaccount.label"/></a></li>
            </ul>
        </div>

       <g:if test="${info != null && !info.isEmpty()}">
            Info: ${info}
        </g:if>


        <g:form name="myForm" update="resultsAjax"
                      url="[controller: 'loan', action: 'getLoan']"
                      onComplete="document.getElementById('myForm').reset();">
                       <div>
                           <label for="amount">Kwota kredytu:</label>
                           <input name="amount" maxlength="50" value="" id="amount" type="text">
                           <label for="desc">Opis:</label>
                           <input name="desc" maxlength="50" value="" id="desc" type="text">
                           <input value="Submit" type="submit">
                       </div>
        </g:form>
	</body>
</html>

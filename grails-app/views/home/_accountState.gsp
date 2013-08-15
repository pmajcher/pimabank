<div style="height: 170px; margin-top: 10px; text-align: center;">
	<div>Stan Twojego konta:</div>
	<div style="font-size: 25px;">
		<span style="${accountState < 0 ? 'color: red;' : 'color: green'}">
		
			 <g:formatNumber number="${accountState}" type="currency"
                            minFractionDigits="2" currencyCode="PLN" />
            
                           
		</span>
	</div>
	<div style="font-size: 13px; height: 40px; padding-top: 10px;">
		Niezatwierdzone <br>operacje na kwotę:
	</div>
	<div style="font-size: 20px;">
	   <span style="${notApprovedAmount > 10 ? 'color: red;' : ''}">
		 <g:formatNumber number="${notApprovedAmount}" type="currency"
                            minFractionDigits="2" currencyCode="PLN" />
		</span>
	</div>

</div>
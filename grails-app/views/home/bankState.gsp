<script>
redrawChart("home/getBankHistory", "Stan skarbca", "Historyczny stan skarbca PimaBanku", "bank-history-chart");
</script>
<h3 style="padding-bottom: 20px;">Stan skarbca PimaBanku</h3>

<h1>W skarbcu PimaBanku znajduje się: 
    
            <span style="${bankState < 0 ? 'color: red;' : 'color: green'}">
        
    <g:formatNumber number="${bankState}" type="currency"
                            minFractionDigits="2" currencyCode="PLN" />
    </span></h1>
    <br>
<h4>Niezatwierdzone operacje na kwotę:  <g:formatNumber number="${bankStateNoApproved}" type="currency"
                            minFractionDigits="2" currencyCode="PLN" /></h4>

<br>
<br>

<div id="bank-history-chart" style="min-width: 300px; height: 300px; margin: 0 auto; padding-bottom: 20px; "></div>

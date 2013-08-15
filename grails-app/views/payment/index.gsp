<script type="text/javascript">
function onCompletePayment() {
	hideWaitPage();
    document.getElementById('paymentForm').reset();
    alert("Zapisano wpłatę. Udaj się do najbliższej placówki PimaBanku aby wpłacić zadeklarowaną kwotę.");
    updateAccountState();
}

function onLoading(){
    showWaitPage();
}

</script>

<h3 style="padding-bottom: 20px;">Wpłata</h3>

<g:formRemote name="paymentForm" update="success"
	url="[controller: 'payment', action: 'pay']"
	onComplete="onCompletePayment();"
	onLoading="onLoading()">
	<div>
		<div>
			<label for="amount">Kwota:</label> <input name="amount"
				maxlength="50" value="" id="amount" type="text"> <label
				for="price">Opis:</label> <input name="desc" class="desc"
				maxlength="50" value="" id="desc" type="text"> <input
				value="Submit" type="submit">
		</div>
	</div>
</g:formRemote>


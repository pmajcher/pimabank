 <script>
 function onCompleteLoan() {
	 hideWaitPage();
     document.getElementById('loanForm').reset();
     alert("Złożono wniosek kredytowy. Udaj się do najbliższej placówki PimaBanku aby podjąć zadeklarowaną kwotę.");
     updateAccountState();
}
function onLoading(){
	 showWaitPage();
}
 </script>

<h3 style="padding-bottom: 20px;">Kredyt</h3>

<g:formRemote name="loanForm"
	url="[controller: 'loan', action: 'getLoan']"
	onLoading="onLoading()"
	onSuccess="onCompleteLoan();"
	before="checkiIfUserIsLogged()">
	<div>
		<label for="amount">Kwota kredytu:</label> <input name="amount"
			maxlength="50" value="" id="amount" type="text"> <label
			for="desc">Opis:</label> <input name="desc" maxlength="50" value=""
			id="desc" type="text"> <input value="Submit" type="submit">
	</div>
</g:formRemote>

package tk.pimabank.utils;

public class PimaBankUtils {

	static double parsePriceFromInput(price){

		def amount = price.replace(",", ".")
		printf "amount: "+amount
		def double d = Double.parseDouble(amount)
		printf "d: "+d
		return d
	} 

}

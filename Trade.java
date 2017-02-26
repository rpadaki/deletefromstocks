import java.util.*;

public class Trade {
	public static void tradebond(int id, boolean buy) {
		// always have orders of 99-101 in
		int sellamount = Bond.q() - Bond.selling() + Bond.limit;
		int buyamount = Bond.q() - Bond.buying() - Bond.limit;
		sell("Bond", 101, sellamount);
		buy("Bond", 99, buyamount);
		return
	}

	public static void tradeval(int id) {
		/* if one is higher than the other, buy the first and sell the second
		issues comes up if one is always higher than the other
		at that point buy if one is more than 10 higher than the other and convert

		so also if one is regularly higher than the other all the time
		we can do a pair of trades that loses us less than 10 but converts
		er this is stictly better than converting
		but you cant do this at any time like converting so we should do this if its usually unbalanced

		so we should always be at a trade limit
		we also want our order on the books instead of responding to orders
		so we want an interval that's larger than 10 wide
		probably 11 wide
		and by interval we mean low is for valbs and high is vale
		or vice versa

		*/ 
		int valelow = Vale.bestBid().price;
		int valehigh = Vale.bestOffer().price;
		int valbzlow = Valbz.bestBid().price;
		int valbzhigh = Valbz.bestOffer().price;

		if (valelow - valbzhigh > 10) {
			int numtoexchange = Math.min(Valbz.bestBid().size, Vale.bestOffer().size);
			int numtoconvert = Math.min(numtoexchange - Valbz.limit + Valbz.q(), numtoexchange - (-1) * Vale.limit + Vale.q());
			if (numtoconvert > 0) {
				convert("Valbz", numtoconvert);
			}
			sell("Vale", valelow, numtoexchange);
			buy("Valbz", valbzhigh, numtoexchange);
		}
		else if (valbzlow - valehigh > 10) {
			int numtoexchange = Math.min(Valbz.bestOffer().size, Vale.bestBid().size);
			int numtoconvert = Math.min(numtoexchange - (-1) * Valbz.limit + Valbz.q(), numtoexchange - Vale.limit + Vale.q());
			if (numtoconvert > 0) {
				convert("Vale", numtoconvert);
			}
			sell("Valbz", valbzlow, numtoexchange);
			buy("Vale", valehigh, numtoexchange);
		}
	}

	pubic static void tradexlf() {
		/* so similarly if ten xlf is more/less than the sum of the components then sell/buy
		and do this until you hit the limit
		in which case you then do so if the difference is more than 100

		same thing goes for this as for the vals
		as in instead of converting, do a series of trades that loses you less than 100
		but do this if its unbalanced

		*/
		return
	}
}
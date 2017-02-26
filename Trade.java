import java.util.*;

public class Trade {
    public static void tradeBonds() {
        Stock Bond = Interface.stocks.get("BOND");
        // always have orders of 99-101 in
        int sellamount = Bond.q() - Bond.selling() + Bond.limit;
        int buyamount = - Bond.q() - Bond.buying() + Bond.limit;
        Interface.sell("BOND", 1001, sellamount);
        Interface.buy("BOND", 999, buyamount);
        return;
    }

    public static void tradeVal() {
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
        Stock Vale = Interface.stocks.get("VALE");
        Stock Valbz = Interface.stocks.get("VALBZ");
        int valelow = Vale.bestBid().price;
        int valehigh = Vale.bestOffer().price;
        int valbzlow = Valbz.bestBid().price;
        int valbzhigh = Valbz.bestOffer().price;

        System.out.println((valelow - valbzhigh) + " " + (valbzlow - valehigh));

        if (valelow - valbzhigh > 0) {
            int numtoexchange = Math.min(Valbz.bestBid().size, Vale.bestOffer().size);
            int numtoconvert = Math.min(numtoexchange - Valbz.limit + Valbz.q(), numtoexchange - (-1) * Vale.limit + Vale.q());
            Interface.sell("VALE", valelow, numtoexchange - numtoconvert);
            Interface.buy("VALBZ", valbzhigh, numtoexchange - numtoconvert);
            if (numtoconvert > 0 && valelow - valbzhigh > (10.0/numtoconvert)) {
                Interface.convert("VALE", "BUY", numtoconvert);
                Interface.sell("VALE", valelow, numtoconvert);
            	Interface.buy("VALBZ", valbzhigh, numtoconvert);
            }
        }
        else if (valbzlow - valehigh > 0) {
            int numtoexchange = Math.min(Valbz.bestOffer().size, Vale.bestBid().size);
            int numtoconvert = Math.min(numtoexchange - (-1) * Valbz.limit + Valbz.q(), numtoexchange - Vale.limit + Vale.q());
            Interface.sell("VALBZ", valbzlow, numtoexchange - numtoconvert);
            Interface.buy("VALE", valehigh, numtoexchange - numtoconvert);
            if (numtoconvert > 0 && valbzlow - valehigh > (10.0/numtoconvert)) {
                Interface.convert("VALE", "SELL", numtoconvert);
                Interface.sell("VALBZ", valbzlow, numtoconvert);
            	Interface.buy("VALE", valehigh, numtoconvert);
            }
        }
    }

    public static void makeMarketVal() {
    	Stock Vale = Interface.stocks.get("VALE");
        Stock Valbz = Interface.stocks.get("VALBZ");
        int valelow = Vale.bestBid().price;
        int valehigh = Vale.bestOffer().price;
        int valbzlow = Valbz.bestBid().price;
        int valbzhigh = Valbz.bestOffer().price;

        int add = 2;

        int sellamount = Vale.q() - Vale.selling() + Vale.limit;
        int buyamount = - Vale.q() - Vale.buying() + Vale.limit;

        int lowprice = valbzlow - add;
        int highprice = valbzhigh + add;

        Interface.sell("VALE", highprice, sellamount);
        Interface.buy("VALE", lowprice, buyamount);

        if (Vale.q() == Vale.limit) {
        	Interface.convert("VALE", "SELL", 12);
        }
        else if (Vale.q() == - Vale.limit) {
        	Interface.convert("VALE", "BUY", 12);
        }
    }

    public static void tradexlf() {
		/* so similarly if ten xlf is more/less than the sum of the components then sell/buy
		and do this until you hit the limit
		in which case you then do so if the difference is more than 100

		same thing goes for this as for the vals
		as in instead of converting, do a series of trades that loses you less than 100
		but do this if its unbalanced

		*/
        return;
    }
}
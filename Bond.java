import java.util.*;

public class Stock {
	public static String add(int id, int size, int p, String stock, boolean buy, PrintWriter to_exchange) {
		String buystring;
		if (buy) {
			buystring = "BUY";
		}
		else {
			buystring = "SELL";
		}
		String message = "ADD " + id + " " + stock.toUpperCase() + " " + buystring + " " + p + " " + size;
		to_exchange.println(message);
		return message;
	}

	public static String convert(int id, int size, String stock, boolean buy, PrintWriter to_exchange) {
		String buystring;
		if (buy) {
			buystring = "BUY";
		}
		else {
			buystring = "SELL";
		}
		String message = "CONVERT " + id + " " + stock.toUpperCase() + " " + buystring + " " + size;
		to_exchange.println(message);
		return message;
	}

	public static String cancel(int id, PrintWriter to_exchange) {
		String message = "CANCEL " + id;
		to_exchange.println(message);
		return message;
	}

	public static String 
}
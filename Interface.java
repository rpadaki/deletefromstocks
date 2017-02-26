import java.lang.*;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.Socket;
import java.util.*;
import java.io.IOException;

public class Interface {
    public static String[] SYMBOLS = {"BOND", "VALBZ", "VALE", "GS", "MS", "WFC", "XLF"};
    public static int[] LIMITS = {100, 10, 10, 100, 100, 100, 100};

    public static Socket skt;
    public static BufferedReader from_exchange;
    public static PrintWriter to_exchange;
    public static HashMap<String,Stock> stocks;

    public static int order_id;
    static HashMap<String,Order> pending_orders, current_orders;


    public static void printToFeed(String s) {
        to_exchange.println(s);
    }

    public static Message parseMessage(String input) {
        StringTokenizer st = new StringTokenizer(input);
        String type = st.nextToken();
        Message message;
        if(type.equals("HELLO")) {
            message = new Hello(st);
            System.out.println(((Hello) message).positions);
        } else if(type.equals("ACK")) {
            message = new Ack(st);
        } else if(type.equals("REJECT")) {
            message = new Reject(st);
        } else if(type.equals("BOOK")) {
            message = new Book(st);
        } else {
            message = new Message();
        }
        message.type = type;
        return message;
    }

    public static Message readFromFeed() throws IOException {
        String input = from_exchange.readLine().trim();
        return parseMessage(input);
    }

    public static void init() throws IOException {
        skt = new Socket("test-exch-deletefromstocks", 20000);
        from_exchange = new BufferedReader(new InputStreamReader(skt.getInputStream()));
        to_exchange = new PrintWriter(skt.getOutputStream(), true);
        printToFeed("HELLO DELETEFROMSTOCKS");

        Random rand = new Random();
        order_id = rand.nextInt();

        for(int i = 0; i < SYMBOLS.length; i++){
            String symbol = SYMBOLS[i];
            int limit = LIMITS[i];
            stocks.put(symbol, new Stock(symbol, limit));
        }
    }

    public static void makeOrder(Order order) {
        printToFeed("ADD" + order.id + " " + order.symbol + " " + order.type + " " + order.pos.price + " " + order.pos.size);
        pending_orders.put(order.id, order);
    }

    public static void buy(String symbol, int price, int size) {
        String id = Integer.toString(order_id++);
        makeOrder(new Order("BUY", id, symbol, price, size));
    }

    public static void sell() {
    }

    public static void convert() {
    }

    public static void ack(Ack m) {
        Order order = pending_orders.get(m.id);
        current_orders.put(order.id, order);
        pending_orders.remove(m.id);
    }

    public static void reject(Reject m) {
        pending_orders.remove(m.id);
    }

    public static void book(Book m) {
    }

    public static void run() throws IOException {
        while(true) {
            try {
                Message m = readFromFeed();
                String type = m.type;
                if(type.equals("HELLO")) {
                } else if(type.equals("ACK")) {
                    ack((Ack) m);
                } else if(type.equals("REJECT")) {
                    reject((Reject) m);
                } else if(type.equals("BOOK")) {
                    book((Book) m);
                } else {
                }
            } catch(Exception e) {
            }
        }
    }

}

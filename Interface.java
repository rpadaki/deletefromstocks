import java.io.*;
import java.lang.*;
import java.net.Socket;
import java.util.*;

public class Interface {
    public static String ERROR_FILE = "error.txt";
    public static String[] SYMBOLS = {"BOND", "VALBZ", "VALE", "GS", "MS", "WFC", "XLF"};
    public static int[] LIMITS = {100, 10, 10, 100, 100, 100, 100};

    public static Socket skt;
    public static BufferedReader from_exchange;
    public static PrintWriter to_exchange;
    public static HashMap<String,Stock> stocks = new HashMap<>();

    public static int order_id;
    static HashMap<String,Order> pending_orders = new HashMap<>();

    public static String hn;
    public static File log = new File(ERROR_FILE);
    public static PrintStream ps;

    public static void printToFeed(String s) {
        to_exchange.println(s);
        System.out.println(s);
    }

    public static Message parseMessage(String input) {
        StringTokenizer st = new StringTokenizer(input);
        String type = st.nextToken();
        if(type.equals("CLOSE")){
            System.exit(0);
        }
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
        } else if(type.equals("FILL")) {
            message = new Fill(st);
        } else if(type.equals("CLOSE")) {
            message = new Close(st);
        } else {
            message = new Message();
            System.out.println("MSG: " + input);
        }
        message.type = type;
        return message;
    }

    public static Message readFromFeed() throws IOException {
        String s = from_exchange.readLine().trim();
        System.out.println(s);
        return parseMessage(s);
    }

    public static void init(String hostname, int port) throws IOException {
        hn = hostname;
        ps = new PrintStream(log);

        Random rand = new Random();
        order_id = Math.abs(rand.nextInt()) % 1000000;

        for(int i = 0; i < SYMBOLS.length; i++){
            String symbol = SYMBOLS[i];
            int limit = LIMITS[i];
            stocks.put(symbol, new Stock(symbol, limit));
        }

        skt = new Socket(hostname, port);
        from_exchange = new BufferedReader(new InputStreamReader(skt.getInputStream()));
        to_exchange = new PrintWriter(skt.getOutputStream(), true);
        printToFeed("HELLO DELETEFROMSTOCKS");
    }

    public static void makeOrder(Order order) {
        printToFeed("ADD " + order.id + " " + order.symbol + " " + order.type + " " + order.pos.price + " " + order.pos.size);
        pending_orders.put(order.id, order);
    }

    public static void buy(String symbol, int price, int size) {
        if(size <= 0) return;
        String id = Integer.toString(order_id++);
        makeOrder(new Order("BUY", id, symbol, price, size, "ADD"));
    }

    public static void sell(String symbol, int price, int size) {
        if(size <= 0) return;
        String id = Integer.toString(order_id++);
        makeOrder(new Order("SELL", id, symbol, price, size, "ADD"));
    }

    public static void convert(String symbol, String type, int size) {
        String id = Integer.toString(order_id++);
        printToFeed("CONVERT " + id + " " + symbol + " " + type + " " + size);
        Order order = new Order(type, id, symbol, -1, size, "CONVERT");
        pending_orders.put(order.id, order);
    }

    public static void ack(Ack m) {
        Order order = pending_orders.get(m.id);
        Stock stock = stocks.get(order.symbol);
        if(order.form.equals("ADD")) {
            if (order.type.equals("BUY")) {
                stock.ourBids.add(order);
            } else {
                stock.ourOffers.add(order);
            }
        } else {
            if(order.symbol.equals("VALE")) {
                Stock vale = stocks.get("VALE");
                Stock valbz = stocks.get("VALBZ");
                if(order.type.equals("BUY")) {
                    vale.portfolio += order.pos.size;
                    valbz.portfolio -= order.pos.size;
                } else {
                    vale.portfolio -= order.pos.size;
                    valbz.portfolio += order.pos.size;
                }
            }
        }
        pending_orders.remove(m.id);
    }

    public static void reject(Reject m) {
        pending_orders.remove(m.id);
    }

    public static void book(Book m) {
        assert(m.buy != null);
        assert(m.sell != null);
        stocks.get(m.symbol).updatePositions(m.buy, m.sell);
    }

    public static void fill(Fill m) {
        Stock stock = stocks.get(m.symbol);
        for(Order ord : stock.ourOffers) {
            if(ord.id.equals(m.id)) {
                ord.pos.size -= m.pos.size;
                stock.portfolio -= m.pos.size;
            }
        }
        for(Order ord : stock.ourBids) {
            if(ord.id.equals(m.id)) {
                ord.pos.size -= m.pos.size;
                stock.portfolio += m.pos.size;
            }
        }
    }

    public static void hello(Hello m) {
        for(int i = 0; i < m.symbols.size(); i++) {
            String symbol = m.symbols.get(i);
            int pos = m.positions.get(i);
            ps.print(symbol);
            stocks.get(symbol).portfolio = pos;
        }
    }

    public static void run() throws IOException {
        while(true) {
            try {
                Message m = readFromFeed();
                String type = m.type;
                if(type.equals("HELLO")) {
                    hello((Hello) m);
                } else if(type.equals("ACK")) {
                    ack((Ack) m);
                } else if(type.equals("REJECT")) {
                    System.out.println("REJECT" + " " + ((Reject) m).id + " " + ((Reject) m).msg);
                    reject((Reject) m);
                } else if(type.equals("BOOK")) {
                    book((Book) m);
                } else if(type.equals("FILL")) {
                    fill((Fill) m);
                } else if(type.equals("CLOSE")) {
                    System.exit(0);
                } else {
                }
            } catch(Exception e) {
                e.printStackTrace(ps);
            }

            for(String symbol : SYMBOLS) {
                Stock stock = stocks.get(symbol);
                System.out.println(symbol + ": " + stock.q() + " " + stock.selling() + " " + stock.buying());
            }

            try {
                System.out.println("TRADE");
                Trade.tradeBonds();
            } catch(Exception e) {
                e.printStackTrace(ps);
            }

            if (!hn.equals("PRODUCTION")) {
                try {
                    System.out.println("TRADE");
                    // Trade.tradeVal();
                } catch(Exception e) {
                    e.printStackTrace(ps);
                }
            }
        }
    }

}

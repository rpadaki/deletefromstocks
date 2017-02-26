import java.lang.*;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.Socket;
import java.util.*;
import java.io.IOException;

public class Interface {
  public static Socket skt;
  public static BufferedReader from_exchange;
  public static PrintWriter to_exchange;

  public static String[] SYMBOLS = {"BOND", "VALBZ", "VALE", "GS", "MS", "WFC", "XLF"};

  public static void communicate(String s) {
    to_exchange.println(s);
  }

  public static Message readFromFeed() throws IOException {
    String input = from_exchange.readLine().trim();
    StringTokenizer st = new StringTokenizer(input);
    String type = st.nextToken();
    Message message;
    if(type.equals("HELLO")) {
      message = new Hello(st);
    } else if(type.equals("ACK")) {
      message = new Ack(st);
    } else if(type.equals("REJECT")) {
      message = new Reject(st);
    } else {
      message = new Message(st);
    }
    message.type = type;
    return message;
  }

  public static void init() {
    try {
      skt = new Socket("test-exch-deletefromstocks", 20000);
      from_exchange = new BufferedReader(new InputStreamReader(skt.getInputStream()));
      to_exchange = new PrintWriter(skt.getOutputStream(), true);
      communicate("HELLO DELETEFROMSTOCKS");
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
  }

  public static void run() {
    while(true) {
      Message m = readFromFeed();
    }
  }

}

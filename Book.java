import java.util.*;

public class Book extends Message {
  String symbol;
  ArrayList<Position> buy, sell;

  public Book(StringTokenizer st) {
    symbol = st.nextToken();
    boolean isBuy = true;
    while(st.hasMoreTokens()) {
      String token = st.nextToken();
      if(token.equals("BUY")){
        continue;
      } else if(token.equals("SELL")){
        isBuy = false;
      } else {
        String[] vals = token.split(":");
        int price = Integer.parseInt(vals[0]);
        int size = Integer.parseInt(vals[1]);
        Position pos = new Position(price, size);
        if(isBuy) {
          buy.add(pos);
        } else {
          sell.add(pos);
        }
      }
    }
  }
}

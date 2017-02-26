public class Order {
  String type, id, symbol, form;
  Position pos;
  long timestamp;
  Order(String type, String id, String symbol, int price, int size, String form) {
    this.type = type;
    this.id = id;
    this.symbol = symbol;
    this.pos = new Position(price, size);
    this.form = form;
    this.timestamp = System.currentTimeMillis();
  }
}

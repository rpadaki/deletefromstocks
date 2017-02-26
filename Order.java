public class Order {
  String type, id, symbol;
  Position pos;
  Order(String type, String id, String symbol, int price, int size) {
    this.type = type;
    this.id = id;
    this.symbol = symbol;
    this.pos = new Position(price, size);
  }
}

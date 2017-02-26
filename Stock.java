import java.util.*;

public class Stock {
  String symbol;
  ArrayList<Position> marketBids, marketOffers;
  ArrayList<Order> ourBids, ourOffers;
  int limit, portfolio;

  public Stock(String symbol, int limit) {
    this.symbol = symbol;
    this.limit = limit;
    ourBids = new ArrayList<>();
    ourOffers = new ArrayList<>();
    marketBids = new ArrayList<>();
    marketOffers = new ArrayList<>();
  }

  public void updatePositions(ArrayList<Position> bids, ArrayList<Position> offers) {
    this.marketBids = bids;
    this.marketOffers = offers;
  }

  public Position bestBid() {
    if(marketBids.size() > 0) {
      return marketBids.get(0);
    } else {
      return null;
    }
  }

  public Position bestOffer() {
    if(marketOffers.size() > 0) {
      return marketOffers.get(0);
    } else {
      return null;
    }
  }

  public int selling() {
      int tot = 0;
      for(Order ord : ourOffers) {
          tot += ord.pos.size;
      }
      return tot;
  }

  public int buying() {
    int tot = 0;
    for(Order ord : ourBids) {
      tot += ord.pos.size;
    }
    return tot;
  }

  public int q() {
    return portfolio;
  }
}

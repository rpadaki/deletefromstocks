import java.util.*;

public class Stock {
  String symbol;
  ArrayList<Position> marketBids, marketOffers;
  int limit;

  public Stock(String symbol, int limit) {
    this.symbol = symbol;
    this.limit = limit;
  }

  public void updatePositions(ArrayList<Position> bids, ArrayList<Position> offers) {
    this.marketBids = bids;
    this.marketOffers = offers;
  }

  public Position bestBid() {
    if(marketBids.size() > 0) {
      return marketBids.get(0);
    } else {
      return -1;
    }
  }

  public Position bestOffer() {
    if(marketOffers.size() > 0) {
      return marketOffers.get(0);
    } else {
      return -1;
    }
  }
}

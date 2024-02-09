package vendingmachne.domain;

public interface StockRepository {
  Stock findById(int drinkId);

  void replace(Stock stock);
}

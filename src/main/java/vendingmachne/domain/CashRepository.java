package vendingmachne.domain;

public interface CashRepository {
  Cash findBy(Currency currency);

  TotalCash getTotalCash();

  void replace(Cash cash);

  void replace(TotalCash totalCash);
}

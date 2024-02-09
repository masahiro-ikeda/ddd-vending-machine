package vendingmachne.infrastructure;

import vendingmachne.domain.Stock;

public class StockDto {
  private int drinkId;
  private int drinkQuantity;

  Stock toEntity() {
    return new Stock(this.drinkId, this.drinkQuantity);
  }

  public void setDrinkId(int drinkId) {
    this.drinkId = drinkId;
  }

  public void setDrinkQuantity(int drinkQuantity) {
    this.drinkQuantity = drinkQuantity;
  }
}

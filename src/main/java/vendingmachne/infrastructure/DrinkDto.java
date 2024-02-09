package vendingmachne.infrastructure;

import vendingmachne.domain.Drink;
import vendingmachne.domain.Price;

import java.math.BigDecimal;

public class DrinkDto {
  private int drinkId;
  private String drinkName;
  private BigDecimal drinkPrice;

  Drink toEntity() {
    return new Drink(this.drinkId, this.drinkName, new Price(this.drinkPrice));
  }

  public void setDrinkId(int drinkId) {
    this.drinkId = drinkId;
  }

  public void setDrinkName(String drinkName) {
    this.drinkName = drinkName;
  }

  public void setDrinkPrice(BigDecimal drinkPrice) {
    this.drinkPrice = drinkPrice;
  }
}

package vendingmachne.domain;

/**
 * ドリンク.
 */
public class Drink {
  private final int drinkId;
  private final String drinkName;
  private final Price drinkPrice;

  public Drink(int drinkId, String drinkName, Price drinkPrice) {
    this.drinkId = drinkId;
    this.drinkName = drinkName;
    this.drinkPrice = drinkPrice;
  }

  int drinkId() {
    return drinkId;
  }

  String drinkName() {
    return drinkName;
  }

  Price drinkPrice() {
    return drinkPrice;
  }
}

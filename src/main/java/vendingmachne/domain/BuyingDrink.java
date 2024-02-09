package vendingmachne.domain;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ドリンク購入.
 */
public class BuyingDrink {
  private final int userId;
  private final Drink drink;
  private final int buyingQuantity;
  private final List<Cash> change;
  private final LocalDateTime buyingAt;

  BuyingDrink(int userId, Drink drink, int buyingQuantity, List<Cash> change) {
    this.userId = userId;
    this.drink = drink;
    this.buyingQuantity = buyingQuantity;
    this.change = change;
    this.buyingAt = LocalDateTime.now();
  }

  public int userId() {
    return userId;
  }

  public Drink drink() {
    return drink;
  }

  public int buyingQuantity() {
    return buyingQuantity;
  }

  public List<Cash> change() {
    return change;
  }

  public LocalDateTime buyingAt() {
    return buyingAt;
  }
}

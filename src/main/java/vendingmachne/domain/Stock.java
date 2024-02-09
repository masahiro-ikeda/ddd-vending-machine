package vendingmachne.domain;

/**
 * ドリンクの在庫.
 */
public class Stock {
  private final int drinkId;
  private final int drinkQuantity;

  private static final int MIN = 0;

  public Stock(int drinkId, int drinkQuantity) {
    if (drinkQuantity < MIN) {
      throw new IllegalArgumentException("在庫数が不正です.");
    }
    this.drinkId = drinkId;
    this.drinkQuantity = drinkQuantity;
  }

  /**
   * 在庫が足りないかどうか.
   */
  boolean isShort(int drinkQuantity) {
    return this.drinkQuantity < drinkQuantity;
  }

  /**
   * 在庫数を減らす.
   */
  public Stock subtract(int subtractQuantity) {
    if (isShort(subtractQuantity)) {
      throw new IllegalArgumentException("在庫が足りません.");
    }
    return new Stock(this.drinkId, this.drinkQuantity - subtractQuantity);
  }

  public int drinkId() {
    return drinkId;
  }

  public int drinkQuantity() {
    return drinkQuantity;
  }
}

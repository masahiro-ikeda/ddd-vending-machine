package vendingmachne.domain;

import java.math.BigDecimal;

/**
 * 支払い中の金額.
 */
public class Paid {
  private final int userId;
  private final Amount paidAmount;

  /**
   * 復元用.
   */
  public Paid(int userId, Amount paidAmount) {
    this.userId = userId;
    this.paidAmount = paidAmount;
  }

  public static Paid newUser(int userId) {
    return new Paid(userId, Amount.minimum());
  }

  public Paid repay() {
    return new Paid(this.userId, Amount.minimum());
  }

  public Paid add(Amount addAmount) {
    return new Paid(userId, paidAmount.add(addAmount));
  }

  public Amount amount() {
    return paidAmount;
  }

  public int userId() {
    return userId;
  }
}

package vendingmachne.domain;

import java.math.BigDecimal;

/**
 * 値段.
 */
public class Price {
  private final BigDecimal value;

  private static final BigDecimal MIN = BigDecimal.ZERO;

  public Price(BigDecimal value) {
    if (value.compareTo(MIN) == -1) {
      throw new IllegalArgumentException("マイナス値は不正です.");
    }
    this.value = value;
  }

  /**
   * 指定した個数の金額を計算.
   */
  Amount totalAmount(int buyingQuantity) {
    return new Amount(this.value.multiply(BigDecimal.valueOf(buyingQuantity)));
  }
}

package vendingmachne.domain;

import java.math.BigDecimal;

/**
 * 現金の保持数.
 */
public class Cash {
  private final Currency currency;
  private final int quantity;

  private static final int MIN = 0;

  public Cash(Currency currency, int quantity) {
    this.currency = currency;
    this.quantity = quantity;
  }

  public Cash add(int addQuantity) {
    if (addQuantity < MIN) {
      throw new IllegalArgumentException();
    }
    return new Cash(this.currency, this.quantity + addQuantity);
  }

  Cash subtract(int subtractQuantity) {
    if (subtractQuantity > this.quantity) {
      throw new IllegalArgumentException("保持数以上の枚数を取り出そうとしています.");
    }
    return new Cash(this.currency, this.quantity - subtractQuantity);
  }

  public Currency currency() {
    return this.currency;
  }

  Amount amount() {
    return new Amount(currency.value().multiply(BigDecimal.valueOf(quantity)));
  }

  public int quantity() {
    return quantity;
  }
}

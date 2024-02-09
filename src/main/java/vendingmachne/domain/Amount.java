package vendingmachne.domain;

import java.math.BigDecimal;

/**
 * 金額.
 */
public class Amount {
  private final BigDecimal value;

  /* 下限値 */
  private static final BigDecimal MIN = BigDecimal.ZERO;

  public Amount(BigDecimal value) {
    if (value.compareTo(MIN) == -1) {
      throw new IllegalArgumentException("マイナス値は不正です.");
    }
    this.value = value;
  }

  /**
   * 加算.
   */
  Amount add(Amount addAmount) {
    return new Amount(value.add(addAmount.value));
  }

  /**
   * 減算.
   */
  Amount subtract(Amount subtractAmount) {
    return new Amount(value.subtract(subtractAmount.value));
  }

  /**
   * 割算.
   * 現金の枚数計算に使うため小数点以下は切り捨て.
   */
  int divide(Amount divideAmount) {
    if (divideAmount.value.equals(BigDecimal.ZERO)) {
      throw new IllegalArgumentException();
    }
    // TODO 非推奨メソッド
    return this.value.divide(divideAmount.value, 0, BigDecimal.ROUND_DOWN).intValue();
  }

  /**
   * 比較対象 未満かどうか.
   */
  boolean isLessThan(Amount other) {
    return this.value.compareTo(other.value) == -1;
  }

  /**
   * 比較対象を超えるかどうか.
   */
  boolean isMoreThan(Amount other) {
    return this.value.compareTo(other.value) == 1;
  }

  public BigDecimal value() {
    return value;
  }

  static Amount minimum() {
    return new Amount(MIN);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (!(obj instanceof Amount)) return false;
    Amount other = (Amount) obj;
    return this.value.compareTo(other.value()) == 0;
  }

}

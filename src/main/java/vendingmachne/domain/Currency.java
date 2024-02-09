package vendingmachne.domain;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * 自動販売機で使える貨幣(円).
 */
public enum Currency {
  TEN(new BigDecimal(10)),
  FIFTY(new BigDecimal(50)),
  HUNDRED(new BigDecimal(100)),
  FIVE_HUNDRED(new BigDecimal(500)),
  THOUSAND(new BigDecimal(1000));

  // 額面
  private final BigDecimal value;

  Currency(BigDecimal value) {
    this.value = value;
  }

  public BigDecimal value() {
    return this.value;
  }

  public Amount amount() {
    return new Amount(value);
  }

  /**
   * 額面を指定して取得.
   */
  public static Currency of(BigDecimal value) {
    return Arrays.stream(values())
        .filter((e) -> e.value.compareTo(value) == 0) // equalsだと小数点が違ってもfalseになるため
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Illegal Yen Currency."));
  }
}

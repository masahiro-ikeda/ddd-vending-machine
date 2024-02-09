package vendingmachne.infrastructure;

import vendingmachne.domain.Currency;
import vendingmachne.domain.Cash;

import java.math.BigDecimal;

public class CashDto {
  private BigDecimal currency;
  private int currencyQuantity;

  public void setCurrency(BigDecimal currency) {
    this.currency = currency;
  }

  public void setCurrencyQuantity(int currencyQuantity) {
    this.currencyQuantity = currencyQuantity;
  }

  Cash toEntity() {
    return new Cash(Currency.of(this.currency), this.currencyQuantity);
  }
}

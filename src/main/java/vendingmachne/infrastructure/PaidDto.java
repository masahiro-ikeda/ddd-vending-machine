package vendingmachne.infrastructure;

import vendingmachne.domain.Amount;
import vendingmachne.domain.Paid;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaidDto {
  private int userId;
  private BigDecimal paidAmount;

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public void setPaidAmount(BigDecimal paidAmount) {
    this.paidAmount = paidAmount;
  }

  Paid toEntity() {
    return new Paid(userId, new Amount(paidAmount));
  }
}

package vendingmachne.domain;

import java.util.List;

/**
 * 返金.
 */
public class Repayment {
  private final int userId;
  private final List<Cash> repaidCash;

  public Repayment(Paid paid, TotalCash totalCash) {
    this.userId = paid.userId();
    repaidCash = totalCash.calculate(paid.amount());
  }

  public Amount amount() {
    Amount result = Amount.minimum();
    repaidCash.forEach(e -> result.add(e.amount()));
    return result;
  }

  public int userId() {
    return userId;
  }

  public List<Cash> repaidCash() {
    return repaidCash;
  }
}

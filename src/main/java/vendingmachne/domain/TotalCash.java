package vendingmachne.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TotalCash {

  /* 保持している全ての現金 */
  private final List<Cash> cash;

  public TotalCash(List<Cash> cash) {
    this.cash = cash;
  }

  /**
   * 金額分の現金を出金した場合の枚数を計算する.
   * 枚数が最小になるよう金額が大きな紙幣・硬貨から取り出す.
   */
  public List<Cash> calculate(final Amount amount) {
    List<Cash> result = new ArrayList<>();

    // 金額が大きい順にソート
    List<Cash> sorted = cash.stream()
        .sorted((s1, s2) -> s2.currency().value().compareTo(s1.currency().value()))
        .collect(Collectors.toList());

    Amount calculatedAmount = new Amount(BigDecimal.ZERO);
    for (Cash cash : sorted) {
      // 未計算残額
      Amount leftAmount = amount.subtract(calculatedAmount);

      // 残額が額面以下の場合は出金できない
      if (leftAmount.isLessThan(new Amount(cash.currency().value()))) {
        continue;
      }

      // 出金可能な枚数を計算
      int calculatedQuantity = 0;
      if (leftAmount.isMoreThan(cash.amount())) {
        calculatedQuantity += cash.quantity();
      } else {
        calculatedQuantity = leftAmount.divide(cash.currency().amount());
      }
      Cash calculated = new Cash(cash.currency(), calculatedQuantity);
      result.add(calculated);
      calculatedAmount = calculatedAmount.add(calculated.amount());
    }
    if (!calculatedAmount.equals(amount)) {
      throw new IllegalStateException("現金が足りず出金しきれません。");
    }
    return result;
  }

  /**
   * 指定枚数を取り出す.
   */
  public TotalCash take(List<Cash> takenCashList) {
    List<Cash> leftCashList = new ArrayList<>();
    for (Cash cash : this.cash) {
      Cash takenCash = takenCashList.stream().filter(e -> e.currency() == cash.currency()).findFirst().orElse(null);
      if (takenCash != null) {
        leftCashList.add(cash.subtract(takenCash.quantity()));
      } else {
        leftCashList.add(cash);
      }
    }
    return new TotalCash(leftCashList);
  }

  public List<Cash> cash() {
    return cash;
  }
}

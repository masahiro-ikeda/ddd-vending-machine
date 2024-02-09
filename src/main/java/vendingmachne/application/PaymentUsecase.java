package vendingmachne.application;

import org.springframework.transaction.annotation.Transactional;
import vendingmachne.domain.*;
import vendingmachne.domain.Cash;
import vendingmachne.domain.CashRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * お金を支払う.
 */
@Service
public class PaymentUsecase {
  private final CashRepository cashRepository;
  private final PaidRepository paidRepository;

  PaymentUsecase(CashRepository cashRepository, PaidRepository paidRepository) {
    this.cashRepository = cashRepository;
    this.paidRepository = paidRepository;
  }

  @Transactional(rollbackFor = Exception.class)
  public Amount pay(int userId, BigDecimal paymentAmount) {
    Currency currency = Currency.of(paymentAmount);

    Paid paid = paidRepository.findByUserId(userId);
    if (paid == null) {
      paid = Paid.newUser(userId);
      paid = paid.add(currency.amount());
      paidRepository.add(paid);
    } else {
      paid = paid.add(currency.amount());
      paidRepository.replace(paid);
    }


    Cash cash = cashRepository.findBy(currency);
    cash = cash.add(1);
    cashRepository.replace(cash);

    return paid.amount();
  }
}

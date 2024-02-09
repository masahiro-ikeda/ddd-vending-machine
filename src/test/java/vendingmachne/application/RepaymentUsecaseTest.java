package vendingmachne.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import vendingmachne.domain.Currency;
import vendingmachne.domain.Paid;
import vendingmachne.domain.PaidRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RepaymentUsecaseTest {

  @Autowired
  RepaymentUsecase repaymentUsecase;

  @Autowired
  PaymentUsecase paymentUsecase;

  @Autowired
  PaidRepository paidRepository;

  @Test
  @Transactional
  void repay() {
    /* given */
    paidRepository.replace(Paid.newUser(1));
    paymentUsecase.pay(1, BigDecimal.valueOf(10));
    paymentUsecase.pay(1, BigDecimal.valueOf(50));
    paymentUsecase.pay(1, BigDecimal.valueOf(100));
    paymentUsecase.pay(1, BigDecimal.valueOf(500));
    paymentUsecase.pay(1, BigDecimal.valueOf(1000));

    /* when */
    var result = repaymentUsecase.repay(1);

    /* then */
    var cashList = result.repaidCash();
    assertEquals(1, cashList.stream().filter(e -> e.currency().equals(Currency.TEN)).findFirst().get().quantity());
    assertEquals(1, cashList.stream().filter(e -> e.currency().equals(Currency.FIFTY)).findFirst().get().quantity());
    assertEquals(1, cashList.stream().filter(e -> e.currency().equals(Currency.HUNDRED)).findFirst().get().quantity());
    assertEquals(1, cashList.stream().filter(e -> e.currency().equals(Currency.FIVE_HUNDRED)).findFirst().get().quantity());
    assertEquals(1, cashList.stream().filter(e -> e.currency().equals(Currency.THOUSAND)).findFirst().get().quantity());
  }

}
package vendingmachne.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import vendingmachne.domain.Paid;
import vendingmachne.domain.PaidRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PaymentUsecaseTest {

  @Autowired
  PaymentUsecase paymentUsecase;

  @Autowired
  PaidRepository paidRepository;

  @Test
  @Transactional
  void pay() {
    paidRepository.replace(Paid.newUser(1));

    var result = paymentUsecase.pay(1, BigDecimal.valueOf(10));
    assertEquals("10.00", result.value().toString());
    result = paymentUsecase.pay(1, BigDecimal.valueOf(50));
    assertEquals("60.00", result.value().toString());
    result = paymentUsecase.pay(1, BigDecimal.valueOf(100));
    assertEquals("160.00", result.value().toString());
    result = paymentUsecase.pay(1, BigDecimal.valueOf(500));
    assertEquals("660.00", result.value().toString());
    result = paymentUsecase.pay(1, BigDecimal.valueOf(1000));
    assertEquals("1660.00", result.value().toString());
  }

}
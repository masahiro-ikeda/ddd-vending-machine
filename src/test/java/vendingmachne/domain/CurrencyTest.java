package vendingmachne.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrencyTest {

  @Test
  void of() {
    var result = Currency.of(BigDecimal.valueOf(100));
    assertEquals(Currency.HUNDRED, result);
  }

}
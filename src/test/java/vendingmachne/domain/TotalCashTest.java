package vendingmachne.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TotalCashTest {

  @Test
  void calculate() {
    /* given */
    List<Cash> cash = Arrays.asList(
        new Cash(Currency.TEN, 10),
        new Cash(Currency.FIFTY, 10),
        new Cash(Currency.HUNDRED, 10),
        new Cash(Currency.FIVE_HUNDRED, 10),
        new Cash(Currency.THOUSAND, 10)
    );
    TotalCash totalCash = new TotalCash(cash);
    Amount testAmount = new Amount(BigDecimal.valueOf(790));

    /* when */
    var result = totalCash.calculate(testAmount);

    /* then */
    assertTrue(result.stream().anyMatch(e -> e.currency().equals(Currency.TEN) && e.quantity() == 4));
    assertTrue(result.stream().anyMatch(e -> e.currency().equals(Currency.FIFTY) && e.quantity() == 1));
    assertTrue(result.stream().anyMatch(e -> e.currency().equals(Currency.HUNDRED) && e.quantity() == 2));
    assertTrue(result.stream().anyMatch(e -> e.currency().equals(Currency.FIVE_HUNDRED) && e.quantity() == 1));
  }

  @Test
  void calculate_short() {
    /* given */
    List<Cash> cash = Arrays.asList(
        new Cash(Currency.TEN, 1),
        new Cash(Currency.FIFTY, 0),
        new Cash(Currency.HUNDRED, 0),
        new Cash(Currency.FIVE_HUNDRED, 0),
        new Cash(Currency.THOUSAND, 0)
    );
    TotalCash totalCash = new TotalCash(cash);
    Amount changeAmount = new Amount(BigDecimal.valueOf(20));

    /* when */
    assertThrows(IllegalStateException.class, () -> totalCash.calculate(changeAmount));
  }

  @Test
  void take() {
    /* given */
    List<Cash> cash = Arrays.asList(
        new Cash(Currency.TEN, 10),
        new Cash(Currency.FIFTY, 10),
        new Cash(Currency.HUNDRED, 10),
        new Cash(Currency.FIVE_HUNDRED, 10),
        new Cash(Currency.THOUSAND, 10)
    );
    TotalCash totalCash = new TotalCash(cash);

    List<Cash> takeStocks = Arrays.asList(
        new Cash(Currency.TEN, 1),
        new Cash(Currency.FIFTY, 2),
        new Cash(Currency.HUNDRED, 3),
        new Cash(Currency.FIVE_HUNDRED, 4),
        new Cash(Currency.THOUSAND, 5)
    );

    /* when */
    var result = totalCash.take(takeStocks);

    /* then */
    var leftStocks = result.cash();
    assertTrue(leftStocks.stream().anyMatch(e -> e.currency().equals(Currency.TEN) && e.quantity() == 9));
    assertTrue(leftStocks.stream().anyMatch(e -> e.currency().equals(Currency.FIFTY) && e.quantity() == 8));
    assertTrue(leftStocks.stream().anyMatch(e -> e.currency().equals(Currency.HUNDRED) && e.quantity() == 7));
    assertTrue(leftStocks.stream().anyMatch(e -> e.currency().equals(Currency.FIVE_HUNDRED) && e.quantity() == 6));
    assertTrue(leftStocks.stream().anyMatch(e -> e.currency().equals(Currency.THOUSAND) && e.quantity() == 5));
  }

  @Test
  void take_short() {
    /* given */
    List<Cash> cash = Arrays.asList(
        new Cash(Currency.TEN, 1),
        new Cash(Currency.FIFTY, 1),
        new Cash(Currency.HUNDRED, 1),
        new Cash(Currency.FIVE_HUNDRED, 1),
        new Cash(Currency.THOUSAND, 1)
    );
    TotalCash totalCash = new TotalCash(cash);

    List<Cash> takeStocks = Arrays.asList(
        new Cash(Currency.TEN, 2),
        new Cash(Currency.FIFTY, 1),
        new Cash(Currency.HUNDRED, 1),
        new Cash(Currency.FIVE_HUNDRED, 1),
        new Cash(Currency.THOUSAND, 1)
    );

    /* when */
    assertThrows(IllegalArgumentException.class, () -> totalCash.take(takeStocks));
  }

}
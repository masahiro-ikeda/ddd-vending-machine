package vendingmachne.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AmountTest {

  @Test
  void constructor() {
    assertThrows(IllegalArgumentException.class, () -> new Amount(BigDecimal.valueOf(-1)));
    assertDoesNotThrow(() -> new Amount(BigDecimal.valueOf(0)));
    assertDoesNotThrow(() -> new Amount(BigDecimal.valueOf(1)));
    assertDoesNotThrow(() -> new Amount(BigDecimal.valueOf(99999)));
  }

  @Test
  void add() {
    var amount1 = new Amount(BigDecimal.valueOf(1));
    var amount2 = new Amount(BigDecimal.valueOf(2));
    var result = amount1.add(amount2);
    assertEquals(BigDecimal.valueOf(3), result.value());
  }

  @Test
  void subtract() {
    /* 単純な減算 */
    {
      var amount1 = new Amount(BigDecimal.valueOf(5));
      var amount2 = new Amount(BigDecimal.valueOf(2));
      var result = amount1.subtract(amount2);
      assertEquals(BigDecimal.valueOf(3), result.value());
    }

    /* 減算後の数値が不正値 */
    {
      var amount1 = new Amount(BigDecimal.valueOf(100));
      var amount2 = new Amount(BigDecimal.valueOf(101));
      assertThrows(IllegalArgumentException.class, () -> amount1.subtract(amount2));
    }
  }

  @Test
  void divide() {
    /* 単純な割り算 */
    {
      var amount1 = new Amount(BigDecimal.valueOf(100));
      var amount2 = new Amount(BigDecimal.valueOf(10));
      var result = amount1.divide(amount2);
      assertEquals(10, result);
    }

    /* ゼロ割り算 */
    {
      var amount1 = new Amount(BigDecimal.valueOf(100));
      var amount2 = new Amount(BigDecimal.valueOf(0));
      assertThrows(IllegalArgumentException.class, () -> amount1.divide(amount2));
    }

    /* 割るほうの数が大きい場合 */
    {
      var amount1 = new Amount(BigDecimal.valueOf(100));
      var amount2 = new Amount(BigDecimal.valueOf(500));
      var result = amount1.divide(amount2);
      assertEquals(0, result);
    }

    /* 小数点が発生する計算 */
    {
      var amount1 = new Amount(BigDecimal.valueOf(100));
      var amount2 = new Amount(BigDecimal.valueOf(51));
      var result = amount1.divide(amount2);
      assertEquals(1, result);
    }
  }

  @Test
  void isLessThan() {
    /* 比較対象のほうが大きい */
    {
      var amount1 = new Amount(BigDecimal.valueOf(1));
      var amount2 = new Amount(BigDecimal.valueOf(2));
      assertTrue(amount1.isLessThan(amount2));
    }

    /* 同値で比較 */
    {
      var amount1 = new Amount(BigDecimal.valueOf(1));
      var amount2 = new Amount(BigDecimal.valueOf(1));
      assertFalse(amount1.isLessThan(amount2));
    }

    /* 比較対象のほうが小さい */
    {
      var amount1 = new Amount(BigDecimal.valueOf(2));
      var amount2 = new Amount(BigDecimal.valueOf(1));
      assertFalse(amount1.isLessThan(amount2));
    }
  }

}
package vendingmachne.application;

import org.springframework.transaction.annotation.Transactional;
import vendingmachne.domain.BuyDrinkService;
import vendingmachne.domain.BuyingDrink;

/**
 * ドリンクを購入する.
 */
public class BuyDrinkUsecase {
  private final BuyDrinkService buyDrinkService;

  BuyDrinkUsecase(BuyDrinkService buyDrinkService) {
    this.buyDrinkService = buyDrinkService;
  }

  @Transactional(rollbackFor = Exception.class)
  BuyingDrink buyDrink(int userId, int drinkId) {
    return buyDrinkService.execute(userId, drinkId, 1);
  }
}

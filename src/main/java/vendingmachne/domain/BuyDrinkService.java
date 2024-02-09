package vendingmachne.domain;

import org.springframework.stereotype.Service;

/**
 * ドリンク購入処理.
 * 複数ドメインにまたがる処理なのでドメインサービスとして統合.
 */
@Service
public class BuyDrinkService {
  private final CashRepository cashRepository;
  private final PaidRepository paidRepository;
  private final DrinkRepository drinkRepository;
  private final StockRepository stockRepository;

  BuyDrinkService(CashRepository cashRepository, PaidRepository paidRepository, DrinkRepository drinkRepository, StockRepository stockRepository) {
    this.cashRepository = cashRepository;
    this.paidRepository = paidRepository;
    this.drinkRepository = drinkRepository;
    this.stockRepository = stockRepository;
  }

  /**
   * ドリンクを購入する.
   */
  public BuyingDrink execute(int userId, int drinkId, int buyingQuantity) {
    Paid paid = paidRepository.findByUserId(userId);
    TotalCash totalCash = cashRepository.getTotalCash();
    Drink drink = drinkRepository.findById(drinkId);
    Stock stock = stockRepository.findById(drinkId);

    // 購入チェック TODO 複雑化しそうなら切り出す
    Amount buyingAmount = drink.drinkPrice().totalAmount(buyingQuantity);
    if (paid.amount().isMoreThan(buyingAmount)) {
      throw new IllegalStateException("支払金額が足りません.");
    }
    if (stock.isShort(buyingQuantity)) {
      throw new IllegalStateException("在庫が足りません.");
    }

    // 購入処理
    Amount changeAmount = paid.amount().subtract(buyingAmount);
    BuyingDrink buyingDrink = new BuyingDrink(userId, drink, buyingQuantity, totalCash.calculate(changeAmount));
    stockRepository.replace(stock.subtract(buyingDrink.buyingQuantity()));
    paidRepository.replace(paid.repay());
    cashRepository.replace(totalCash.take(buyingDrink.change()));

    return buyingDrink;
  }
}

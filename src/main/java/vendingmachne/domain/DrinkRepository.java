package vendingmachne.domain;

public interface DrinkRepository {
  Drink findById(int drinkId);
}

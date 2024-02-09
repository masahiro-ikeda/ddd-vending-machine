package vendingmachne.domain;

public interface PaidRepository {
  Paid findByUserId(int userId);

  void add(Paid paid);

  void replace(Paid paid);
}

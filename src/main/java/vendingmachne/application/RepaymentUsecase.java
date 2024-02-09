package vendingmachne.application;

import org.springframework.transaction.annotation.Transactional;
import vendingmachne.domain.Paid;
import vendingmachne.domain.PaidRepository;
import vendingmachne.domain.TotalCash;
import vendingmachne.domain.CashRepository;
import vendingmachne.domain.Repayment;
import org.springframework.stereotype.Service;

/**
 * 返金する.
 */
@Service
public class RepaymentUsecase {

  private final PaidRepository paidRepository;
  private final CashRepository cashRepository;

  RepaymentUsecase(PaidRepository paidRepository, CashRepository cashRepository) {
    this.paidRepository = paidRepository;
    this.cashRepository = cashRepository;
  }

  @Transactional(rollbackFor = Exception.class)
  public Repayment repay(int userId) {
    Paid paid = paidRepository.findByUserId(userId);
    TotalCash totalCash = cashRepository.getTotalCash();

    Repayment repayment = new Repayment(paid, totalCash);

    Paid repaid = paid.repay();
    paidRepository.replace(repaid);
    TotalCash leftCash = totalCash.take(repayment.repaidCash());
    cashRepository.replace(leftCash);

    return repayment;
  }
}

package sandbox.fis.api.repository.amount;

import org.springframework.data.jpa.repository.JpaRepository;
import sandbox.fis.api.entity.amount.CompanyAmount;

public interface CompanyAmountRepository extends JpaRepository<CompanyAmount, Long> {

}

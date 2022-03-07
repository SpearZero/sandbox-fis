package sandbox.fis.api.repository.amount;

import org.springframework.data.jpa.repository.JpaRepository;
import sandbox.fis.api.entity.amount.Amount;

public interface AmountRepository extends JpaRepository<Amount, Long> {

}

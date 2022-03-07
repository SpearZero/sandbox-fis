package sandbox.fis.api.repository.amount;

import org.springframework.data.jpa.repository.JpaRepository;
import sandbox.fis.api.entity.amount.CreatorAmount;

public interface CreatorAmountRepository extends JpaRepository<CreatorAmount, Long> {

}

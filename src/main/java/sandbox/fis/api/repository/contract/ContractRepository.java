package sandbox.fis.api.repository.contract;

import org.springframework.data.jpa.repository.JpaRepository;
import sandbox.fis.api.entity.contract.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long> {

}

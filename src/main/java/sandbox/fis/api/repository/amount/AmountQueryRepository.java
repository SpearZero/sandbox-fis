package sandbox.fis.api.repository.amount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sandbox.fis.api.entity.amount.Amount;

import javax.persistence.Tuple;

public interface AmountQueryRepository extends JpaRepository<Amount, Long> {

    // 회사 총 매출 조회
    @Query(value = "SELECT CONVERT(SUM(am.amount), CHAR) as amount " +
            "FROM amount as am " +
            "WHERE am.contract_id = :contractId AND DATE_FORMAT(am.day, '%Y-%m') = :month " +
            "GROUP BY DATE_FORMAT(am.day, '%Y-%m')", nativeQuery = true)
    Tuple findCompanyTotalAmounts(@Param("contractId") Long contractId, @Param("month") String month);

    // 회사 순 매출 조회
    @Query(value = "SELECT CONVERT(SUM(cm.amount), CHAR) as amount " +
            "FROM company_amount as cm " +
            "WHERE cm.contract_id = :contractId AND DATE_FORMAT(cm.day, '%Y-%m') = :month " +
            "GROUP BY DATE_FORMAT(cm.day, '%Y-%m')", nativeQuery = true)
    Tuple findCompanyNetAmounts(@Param("contractId") Long contractId, @Param("month") String month);

}

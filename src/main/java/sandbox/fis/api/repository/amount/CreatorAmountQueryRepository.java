package sandbox.fis.api.repository.amount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sandbox.fis.api.entity.amount.CreatorAmount;

import javax.persistence.Tuple;
import java.util.List;

public interface CreatorAmountQueryRepository extends JpaRepository<CreatorAmount, Long> {

    // 크리에이터들의 정산 조회
    @Query(value = "SELECT CONVERT(SUM(ca.amount), CHAR) as amount, DATE_FORMAT(ca.day, '%Y-%m') as month " +
            "FROM creator_amount as ca JOIN contract as ct ON ca.contract_id = ct.contract_id " +
            "WHERE DATE_FORMAT(ca.day, '%Y-%m') BETWEEN :startMonth AND :endMonth " +
            "AND ca.contract_id = :contractId GROUP BY DATE_FORMAT(ca.day, '%Y-%m') " +
            "ORDER BY DATE_FORMAT(ca.day, '%Y-%m') DESC", nativeQuery = true)
    List<Tuple> findCreatorAmounts(@Param("contractId") Long contractId, @Param("startMonth") String startMonth,
                                   @Param("endMonth") String endMonth);

    // 개인 크리에이터 정산 조회
    @Query(value = "SELECT CONVERT(SUM(ca.amount), CHAR) as amount, DATE_FORMAT(ca.day, '%Y-%m') as month " +
            "FROM creator_amount as ca JOIN contract ct ON ca.contract_id = ct.contract_id " +
            "WHERE DATE_FORMAT(ca.day, '%Y-%m') BETWEEN :startMonth AND :endMonth " +
            "AND ca.contract_id = :contractId AND ca.creator_id = :creatorId " +
            "GROUP BY DATE_FORMAT(ca.day, '%Y-%m') " +
            "ORDER BY DATE_FORMAT(ca.day, '%Y-%m') DESC", nativeQuery = true)
    List<Tuple> findPerCreatorAmounts(@Param("contractId") Long contractId, @Param("creatorId") Long creatorId,
                                      @Param("startMonth") String startMonth, @Param("endMonth") String endMonth);

}

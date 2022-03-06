package sandbox.fis.api.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@DisplayName("RsCalculatorImpl 테스트")
class RsCalculatorImplTest {

    static RsCalculator rsCalculator;

    @BeforeAll
    static void setUp() {
        rsCalculator = new RsCalculatorImpl();
    }

    final Integer companyRs = 50;
    final Integer creatorRs = 50;

    final Integer overCompanyRs = 50;
    final Integer overCreatorRs = 55;

    final Integer underCompanyRs = 50;
    final Integer underCreatorRs = 45;

    final List<Integer> creatorsRs = Stream.of(30, 70).collect(Collectors.toList());
    final List<Integer> overCreatorsRs = Stream.of(30, 71).collect(Collectors.toList());
    final List<Integer> underCreatorsRs = Stream.of(30, 69).collect(Collectors.toList());

    @Test
    @DisplayName("회사RS와 크리에이터RS의 합이 100 초과면 false 반환")
    void companyRs_creatorRs_sum_over_100_then_return_false() {
        // when
        boolean rsResult = rsCalculator.rsContractSumValid(overCompanyRs, overCreatorRs);

        // then
        assertThat(rsResult).isFalse();
    }
    
    @Test
    @DisplayName("회사RS와 크리에이터RS의 합이 100 미만이면 false 반환")
    void companyRs_creatorRs_sum_under_100_then_return_false() {
        // when
        boolean rsResult = rsCalculator.rsContractSumValid(underCompanyRs, underCreatorRs);

        // then
        assertThat(rsResult).isFalse();
    }
    
    @Test
    @DisplayName("회사RS와 크리에이터RS의 합이 100이 되면 true 반환")
    void companyRs_creatorRs_sum_100_then_return_true() {
        // when
        boolean rsResult = rsCalculator.rsContractSumValid(companyRs, creatorRs);

        // then
        assertThat(rsResult).isTrue();
    }
    
    @Test
    @DisplayName("크리에이터들RS의 합이 100 초과면 false 반환")
    void creatorRs_sum_over_100_then_return_false() {
        // when
        boolean rsResult = rsCalculator.rsContractCreatorsSumValid(overCreatorsRs);

        // then
        assertThat(rsResult).isFalse();
    }
    
    @Test
    @DisplayName("크리에이터들RS의 합이 100 미만이면 false 반환")
    void creatorsRs_sum_under_100_then_return_false() {
        // when
        boolean rsResult = rsCalculator.rsContractCreatorsSumValid(underCreatorsRs);

        // then
        assertThat(rsResult).isFalse();
    }
    
    @Test
    @DisplayName("크리에이터들RS의 합이 100이 되면 true 반환")
    void creatorsRs_sum_100_then_return_true() {
        // when
        boolean rsResult = rsCalculator.rsContractCreatorsSumValid(creatorsRs);

        // then
        assertThat(rsResult).isTrue();
    }
}
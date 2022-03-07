package sandbox.fis.api.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;


@DisplayName("AmountCalculatorImpl 테스트")
class AmountCalculatorImplTest {

    static AmountCalculator amountCalculator;

    @BeforeAll
    static void setUp() {
        amountCalculator = new AmountCalculatorImpl();
    }

    static Stream<BigDecimal> amounts() {
        return Stream.of(new BigDecimal("12345.53").setScale(7), new BigDecimal("393939.35").setScale(7),
                new BigDecimal("11224.31").setScale(7), new BigDecimal("9999.14").setScale(7),
                new BigDecimal("59393.12").setScale(7));
    }

    @ParameterizedTest(name = "{index} - input amount = {0}")
    @MethodSource("amounts")
    @DisplayName("회사와 크리에이터들간의 Amount 비율이 제대로 계산된다")
    void Company_Creators_Amount_Rate_Calculate_success(BigDecimal amount) {
        for (int i = 0; i <= 100; i++) {
            int companyRate = i;
            int creatorsRate = 100 - i;

            Assertions.assertThat(isAmountSumValid(amount, companyRate, creatorsRate)).isTrue();
        }
    }

    boolean isAmountSumValid(BigDecimal amount, Integer companyRate, Integer creatorRate) {
        BigDecimal companyResult = amountCalculator.calculateAmount(amount, companyRate);
        BigDecimal creatorsResult = amountCalculator.calculateAmount(amount, creatorRate);

        return companyResult.add(creatorsResult).equals(amount);
    }

    @ParameterizedTest(name = "{index} - input amount = {0}")
    @MethodSource("amounts")
    @DisplayName("크리에이터들간의 Amount 비율이 제대로 계산된다")
    void Creators_Amount_Rate_Calculate_success(BigDecimal amount) {
        for (int i = 0; i <= 100; i++) {
            int companyRate = i;
            int creatorsRate = 100 - i;

            BigDecimal companyAmount = amountCalculator.calculateAmount(amount, companyRate);
            BigDecimal creatorsAmount = amountCalculator.calculateAmount(amount, creatorsRate);

            int creatorRate1 = i;
            int creatorRate2 = 100 - i;

            BigDecimal creatorAmount1 = amountCalculator.calculateCreatorsAmount(creatorsAmount, creatorRate1);
            BigDecimal creatorAmount2 = amountCalculator.calculateCreatorsAmount(creatorsAmount, creatorRate2);

            Assertions.assertThat(amount).isEqualTo(companyAmount.add(creatorAmount1.add(creatorAmount2)));
        }
    }
}
package sandbox.fis.api.dto.amount;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@DisplayName("AmountRequest 테스트")
class AmountRequestTest {

    static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    final Long contractId = 1L;
    final LocalDate day = LocalDate.of(2022, 3, 7);
    final BigDecimal amount = new BigDecimal("50301204.12");
    
    @Test
    @DisplayName("계약서 ID가 1 미만이면 검증이 통과되지 않음")
    void AmountRequest_contractId_under_1_then_AmountRequest_contractId_fail() {
        // given
        AmountRequest amountRequest = new AmountRequest(0L, day, amount);

        // when
        Set<ConstraintViolation<AmountRequest>> validate = validator.validate(amountRequest);

        // then
        assertThat(validate.isEmpty()).isFalse();
    }

    static Stream<BigDecimal> invalidAmount() {
        return Stream.of(new BigDecimal("1234422.1233"), new BigDecimal("1222222244444444.12"));
    }

    @ParameterizedTest(name = "{index} - input amount = {0}")
    @MethodSource("invalidAmount")
    @DisplayName("계약서 금액이 유효하지 않으면 검증이 통과되지 않음")
    void AmountRequest_amount_invalid_then_AmountRequest_amount_fail(BigDecimal amount) {
        // given
        AmountRequest amountRequest = new AmountRequest(contractId, day, amount);

        // when
        Set<ConstraintViolation<AmountRequest>> validate = validator.validate(amountRequest);

        // then
        assertThat(validate.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("계약서 필드가 유효하면 검증이 통과됨")
    void AmountRequest_valid_then_AmountRequest_success() {
        // given
        AmountRequest amountRequest = new AmountRequest(contractId, day, amount);

        // when
        Set<ConstraintViolation<AmountRequest>> validate = validator.validate(amountRequest);

        // then
        assertThat(validate.isEmpty()).isTrue();
    }
}
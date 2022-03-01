package sandbox.fis.api.dto.contract;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ContractCreatorRequest 테스트")
class ContractCreatorRequestTest {

    static Validator validator;

    final Long creatorId = 1L;
    final Integer creatorRs = 50;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    
    @Test
    @DisplayName("계약서 크리에이터 크리에이터ID가 null이 입력될 경우 검증이 통과되지 않음")
    void ContractCreatorRequest_creatorId_null_then_ContractCreatorRequest_creatorId_fail() {
        // given
        ContractCreatorRequest contractCreatorRequest = new ContractCreatorRequest(null, creatorRs);

        // when
        Set<ConstraintViolation<ContractCreatorRequest>> validate = validator.validate(contractCreatorRequest);

        // then
        assertThat(validate.isEmpty()).isFalse();
    }
    
    @Test
    @DisplayName("계약서 크리에이터 크리에이터RS가 null이 입력될 경우 검증이 통과되지 않음")
    void ContractCreatorRequest_creatorRs_null_then_ContractCreatorRequest_creatorRs_fail() {
        // given
        ContractCreatorRequest contractCreatorRequest = new ContractCreatorRequest(creatorId, null);

        // when
        Set<ConstraintViolation<ContractCreatorRequest>> validate = validator.validate(contractCreatorRequest);

        // then
        assertThat(validate.isEmpty()).isFalse();
    }
    
    @Test
    @DisplayName("계약서 크리에이터 크리에이터ID가 1미만일 경우 검증이 통과되지 않음")
    void ContractCreatorRequest_creatorId_lower_than_1_then_ContractCreatorRequest_creatorId_fail() {
        // given
        ContractCreatorRequest contractCreatorRequest = new ContractCreatorRequest(0L, creatorRs);

        // when
        Set<ConstraintViolation<ContractCreatorRequest>> validate = validator.validate(contractCreatorRequest);

        // then
        assertThat(validate.isEmpty()).isFalse();
    }
    
    @Test
    @DisplayName("계약서 크리에이터 크리에이터RS가 0미만일 경우 검증이 통과되지 않음")
    void ContractCreatorRequest_creatorRs_lower_than_0_ContractCreatorRequest_creatorRs_fail() {
        // given
        ContractCreatorRequest contractCreatorRequest = new ContractCreatorRequest(creatorId, -1);

        // when
        Set<ConstraintViolation<ContractCreatorRequest>> validate = validator.validate(contractCreatorRequest);

        // then
        assertThat(validate.isEmpty()).isFalse();
    }
    
    @Test
    @DisplayName("계약서 크리에이터 크리에이터RS가 100초과일 경우 검증이 통과되지 않음")
    void ContractCreatorRequest_creatorRs_more_than_100_then_ContractCreatorRequest_creatorRs_fail() {
        // given
        ContractCreatorRequest contractCreatorRequest = new ContractCreatorRequest(creatorId, 101);

        // when
        Set<ConstraintViolation<ContractCreatorRequest>> validate = validator.validate(contractCreatorRequest);

        // then
        assertThat(validate.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("계약서 크리에이터 객체가 유효할 경우 검증이 통과됨")
    void ContractCreatorRequest_valid_then_ContractCreatorRequest_success() {
        // given
        ContractCreatorRequest contractCreatorRequest = new ContractCreatorRequest(creatorId, creatorRs);

        // when
        Set<ConstraintViolation<ContractCreatorRequest>> validate = validator.validate(contractCreatorRequest);

        // then
        assertThat(validate.isEmpty()).isTrue();
    }
}
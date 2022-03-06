package sandbox.fis.api.dto.contract;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ContractChannelRequest 테스트")
class ContractChannelRequestTest {

    static Validator validator;

    final Long channelId = 1L;
    final Integer companyRs = 50;
    final Integer creatorsRs = 50;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("계약서 채널 채널ID가 null이 입력될 경우 검증이 통과되지 않음")
    void ContractChannelRequest_channelId_null_then_ContractChannelRequest_channelId_fail() {
        // given
        ContractCompanyRequest contractCompanyRequest = new ContractCompanyRequest(null, companyRs, creatorsRs);

        // when
        Set<ConstraintViolation<ContractCompanyRequest>> validate = validator.validate(contractCompanyRequest);

        // then
        assertThat(validate.isEmpty()).isFalse();
    }
    
    @Test
    @DisplayName("계약서 채널 회사RS가 null이 입력될 경우 검증이 통과되지 않음")
    void ContractChannelRequest_channelRs_null_then_ContractChannelRequest_channelRs_fail() {
        // given
        ContractCompanyRequest contractCompanyRequest = new ContractCompanyRequest(channelId, null, creatorsRs);

        // when
        Set<ConstraintViolation<ContractCompanyRequest>> validate = validator.validate(contractCompanyRequest);

        // then
        assertThat(validate.isEmpty()).isFalse();
    }
    
    @Test
    @DisplayName("계약서 채널 크리에이터RS가 null이 입력될 경우 검증이 통과되지 않음")
    void ContractChannelRequest_creatorsRs_null_then_ContractChannelRequest_creatorsRs_fail() {
        // given
        ContractCompanyRequest contractCompanyRequest = new ContractCompanyRequest(channelId, companyRs, null);

        // when
        Set<ConstraintViolation<ContractCompanyRequest>> validate = validator.validate(contractCompanyRequest);

        // then
        assertThat(validate.isEmpty()).isFalse();
    }
    
    @Test
    @DisplayName("계약서 채널 채널ID가 1미만일 경우 검증이 통과되지 않음")
    void ContractChannelRequest_channelId_lower_than_1_then_ContractChannelRequest_channelId_fail() {
        // given
        ContractCompanyRequest contractCompanyRequest = new ContractCompanyRequest(0L, companyRs, creatorsRs);

        // when
        Set<ConstraintViolation<ContractCompanyRequest>> validate = validator.validate(contractCompanyRequest);

        // then
        assertThat(validate.isEmpty()).isFalse();
    }
    
    @Test
    @DisplayName("계약서 채널 회사RS가 0미만일 경우 검증이 통과되지 않음")
    void ContractChannelRequest_companyRs_lower_than_0_then_ContractChannelRequest_companyRs_fail() {
        // given
        ContractCompanyRequest contractCompanyRequest = new ContractCompanyRequest(channelId, -1, creatorsRs);

        // when
        Set<ConstraintViolation<ContractCompanyRequest>> validate = validator.validate(contractCompanyRequest);

        // then
        assertThat(validate.isEmpty()).isFalse();
    }
    
    @Test
    @DisplayName("계약서 채널 회사RS가 100초과일 경우 검증이 통과되지 않음")
    void ContractChannelRequest_companyRs_more_than_100_then_ContractChannelRequest_companyRs_fail() {
        // given
        ContractCompanyRequest contractCompanyRequest = new ContractCompanyRequest(channelId, 101, creatorsRs);

        // when
        Set<ConstraintViolation<ContractCompanyRequest>> validate = validator.validate(contractCompanyRequest);

        // then
        assertThat(validate.isEmpty()).isFalse();
    }
    
    @Test
    @DisplayName("계약서 채널 크리에이터RS가 0미만일 경우 검증이 통과되지 않음")
    void ContractChannelRequest_creatorsRs_lower_than_0_then_ContractChannelRequest_creatorsRs_fail() {
        // given
        ContractCompanyRequest contractCompanyRequest = new ContractCompanyRequest(channelId, companyRs, -1);

        // when
        Set<ConstraintViolation<ContractCompanyRequest>> validate = validator.validate(contractCompanyRequest);

        // then
        assertThat(validate.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("계약서 채널 회사RS가 100초과일 경우 검증이 통과되지 않음")
    void ContractChannelRequest_creatorsRs_more_than_100_than_ContractChannelRequest_creatorsRs_fail() {
        // given
        ContractCompanyRequest contractCompanyRequest = new ContractCompanyRequest(channelId, companyRs, 101);

        // when
        Set<ConstraintViolation<ContractCompanyRequest>> validate = validator.validate(contractCompanyRequest);

        // then
        assertThat(validate.isEmpty()).isFalse();
    }
    
    @Test
    @DisplayName("계약서 채널 객체가 유효할 경우 검증이 통과됨")
    void ContractChannelRequest_valid_then_ContractChannelRequest_success() {
        // given
        ContractCompanyRequest contractCompanyRequest = new ContractCompanyRequest(channelId, companyRs, creatorsRs);

        // when
        Set<ConstraintViolation<ContractCompanyRequest>> validate = validator.validate(contractCompanyRequest);

        // then
        assertThat(validate.isEmpty()).isTrue();
    }
}
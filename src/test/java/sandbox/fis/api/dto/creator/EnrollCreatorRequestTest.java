package sandbox.fis.api.dto.creator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@DisplayName("EnrollCreatorRequest 테스트")
class EnrollCreatorRequestTest {

    static Validator validator;

    final String creatorName = "creator";
    final String creatorEmail = "email@email.com";

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    static Stream<String> blankValue() {
        return Stream.of(null, "", " ", " ");
    }
    
    @ParameterizedTest(name = "{index} - input creatorName = {0}")
    @MethodSource("blankValue")
    @DisplayName("크리에이터명에 공백 또는 null이 입력될 경우 검증이 통과되지 않음")
    void EnrollCreatorRequest_creatorName_blank_or_null_then_EnrollCreatorRequest_creatorName_fail(String creatorName) {
        // given
        EnrollCreatorRequest enrollCreatorRequest = new EnrollCreatorRequest(creatorName, creatorEmail);

        // when
        Set<ConstraintViolation<EnrollCreatorRequest>> validate = validator.validate(enrollCreatorRequest);

        // then
        assertThat(validate.isEmpty()).isFalse();
    }

    @ParameterizedTest(name = "{index} - input Email = {0}")
    @MethodSource("blankValue")
    @DisplayName("크리에이터 이메일에 공백 또는 null이 입력될 경우 검증이 통과되지 않음")
    void EnrollCreatorRequest_creatorEmail_blank_or_null_then_EnrollCreatorRequest_creatorEmail_fail(String creatorEmail) {
        // given
        EnrollCreatorRequest enrollCreatorRequest = new EnrollCreatorRequest(creatorName, creatorEmail);

        // when
        Set<ConstraintViolation<EnrollCreatorRequest>> validate = validator.validate(enrollCreatorRequest);

        // then
        assertThat(validate.isEmpty()).isFalse();
    }

    static Stream<String> overLengthCreatorName() {
        return Stream.of("크리에이터명18자크리에이터명18자크");
    }

    @ParameterizedTest(name = "{index} - input creatorName = {0}")
    @MethodSource("overLengthCreatorName")
    @DisplayName("크리에이터명이 18자 이상 초과 입력될 경우 검증이 통과되지 않음")
    void EnrollCreatorRequest_creatorName_length_over_then_EnrollCreatorRequest_creatorName_fail(String creatorName) {
        // given
        EnrollCreatorRequest enrollCreatorRequest = new EnrollCreatorRequest(creatorName, creatorEmail);

        // when
        Set<ConstraintViolation<EnrollCreatorRequest>> validate = validator.validate(enrollCreatorRequest);

        // then
        assertThat(validate.isEmpty()).isFalse();
    }

    static Stream<String> validCreatorName() {
        return Stream.of("크리에이터", "크리에이터12", "크리에이이터");
    }

    @ParameterizedTest(name = "{index} - input creatorName = {0}")
    @MethodSource("validCreatorName")
    @DisplayName("크리에이터명에 유효한 값이 입력될 경우 검증이 통과됨")
    void EnrollCreatorRequest_creatorName_valid_then_EnrollCreatorRequest_creatorName_success(String creatorName) {
        // given
        EnrollCreatorRequest enrollCreatorRequest = new EnrollCreatorRequest(creatorName, creatorEmail);

        // when
        Set<ConstraintViolation<EnrollCreatorRequest>> validate = validator.validate(enrollCreatorRequest);

        // then
        assertThat(validate.isEmpty()).isTrue();
    }

    static Stream<String> invalidCreatorEmail() {
        return Stream.of("invalid", "invalid@");
    }

    @ParameterizedTest(name = "{index} - input creatorEmail = {0}")
    @MethodSource("invalidCreatorEmail")
    @DisplayName("크리에이터 이메일에 유효하지 않은 값이 입력될 경우 검증이 통과되지 않음")
    void EnrollCreatorRequest_creatorEmail_invalid_then_EnrollCreatorRequest_creatorEmail_fail(String creatorEmail) {
        // given
        EnrollCreatorRequest enrollCreatorRequest = new EnrollCreatorRequest(creatorName, creatorEmail);

        // when
        Set<ConstraintViolation<EnrollCreatorRequest>> validate = validator.validate(enrollCreatorRequest);

        // then
        assertThat(validate.isEmpty()).isFalse();
    }

    static Stream<String> validCreatorEmail() {
        return Stream.of("valid@valid.com", "test@test.com");
    }

    @ParameterizedTest(name = "{index} - input creatorEmail = {0}")
    @MethodSource("validCreatorEmail")
    @DisplayName("크리에이터 이메일에 유효한 값이 입력될 경우 검증이 통과됨")
    void EnrollCreatorRequest_creatorEmail_valid_then_EnrollCreatorRequest_creatorEmail_success(String creatorEmail) {
        // given
        EnrollCreatorRequest enrollCreatorRequest = new EnrollCreatorRequest(creatorName, creatorEmail);

        // when
        Set<ConstraintViolation<EnrollCreatorRequest>> validate = validator.validate(enrollCreatorRequest);

        // then
        assertThat(validate.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("유효한 크리에이터명과 이메일이 입력될 경우 검증이 통과됨")
    void EnrollCreatorRequest_valid_then_EnrollCreatorRequest_success() {
        // given
        EnrollCreatorRequest enrollCreatorRequest = new EnrollCreatorRequest(creatorName, creatorEmail);

        // when
        Set<ConstraintViolation<EnrollCreatorRequest>> validate = validator.validate(enrollCreatorRequest);

        // then
        assertThat(validate.isEmpty()).isTrue();
    }
}
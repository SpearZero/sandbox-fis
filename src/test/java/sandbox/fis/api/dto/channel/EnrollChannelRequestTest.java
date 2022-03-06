package sandbox.fis.api.dto.channel;

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

@DisplayName("EnrollChannelRequest 테스트")
class EnrollChannelRequestTest {

    static Validator validator;

    final String channelName = "channel";
    final String channelEmail = "email@email.com";

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    static Stream<String> blankValue() {
        return Stream.of(null, "", " ", " ");
    }

    @ParameterizedTest(name = "{index} - input channelName = {0}")
    @MethodSource("blankValue")
    @DisplayName("채널명에 공백 또는 null이 입력될 경우 검증이 통과되지 않음")
    void EnrollChannelRequest_channelName_blank_or_null_then_EnrollChannelRequest_channelName_fail(String channelName) {
        // given
        EnrollChannelRequest enrollChannelRequest = new EnrollChannelRequest(channelName, channelEmail);

        // when
        Set<ConstraintViolation<EnrollChannelRequest>> validate = validator.validate(enrollChannelRequest);

        // then
        assertThat(validate.isEmpty()).isFalse();
    }

    @ParameterizedTest(name = "{index} - input channelEmail = {0}")
    @MethodSource("blankValue")
    @DisplayName("채널이메일에 공백 또는 null이 입력될 경우 검증이 통과되지 않음")
    void EnrollChannelRequest_channelEmail_blank_or_null_then_EnrollChannelRequest_channelEmail_fail(String channelEmail) {
        // given
        EnrollChannelRequest enrollChannelRequest = new EnrollChannelRequest(channelName, channelEmail);

        // when
        Set<ConstraintViolation<EnrollChannelRequest>> validate = validator.validate(enrollChannelRequest);

        // then
        assertThat(validate.isEmpty()).isFalse();
    }

    static Stream<String> overLengthChannelName() {
        return Stream.of("채널명30자이상채널명30자이상채널명30자이상채널명30자이");
    }

    @ParameterizedTest(name = "{index} - input channelName = {0}")
    @MethodSource("overLengthChannelName")
    @DisplayName("채널명이 30자 초과 입력될 경우 검증이 통과되지 않음")
    void EnrollChannelRequest_channelName_length_over_then_EnrollChannelRequest_channelName_fail(String channelName) {
        // given
        EnrollChannelRequest enrollChannelRequest = new EnrollChannelRequest(channelName, channelEmail);

        // when
        Set<ConstraintViolation<EnrollChannelRequest>> validate = validator.validate(enrollChannelRequest);

        // then
        assertThat(validate.isEmpty()).isFalse();
    }

    static Stream<String> validChannelName() {
        return Stream.of("채널명", "채널입니다", "채널채널");
    }

    @ParameterizedTest(name = "{index} - input channelName = {0}")
    @MethodSource("validChannelName")
    @DisplayName("채널명에 유효한 값이 입력될 경우 검증이 통과됨")
    void EnrollChannelRequest_channelName_valid_then_EnrollChannelRequest_channelName_success(String channelName) {
        // given
        EnrollChannelRequest enrollChannelRequest = new EnrollChannelRequest(channelName, channelEmail);

        // when
        Set<ConstraintViolation<EnrollChannelRequest>> validate = validator.validate(enrollChannelRequest);

        // then
        assertThat(validate.isEmpty()).isTrue();
    }

    static Stream<String> invalidChannelEmail() {
        return Stream.of("invalid", "invalid@");
    }

    @ParameterizedTest(name = "{index} - input channelEmail = {0}")
    @MethodSource("invalidChannelEmail")
    @DisplayName("채널 이메일에 유효하지 않은 값이 입력될 경우 검증이 통과되지 않음")
    void EnrollChannelRequest_channelEmail_invalid_then_EnrollChannelRequest_channelEmail_fail(String channelEmail) {
        // given
        EnrollChannelRequest enrollChannelRequest = new EnrollChannelRequest(channelName, channelEmail);

        // when
        Set<ConstraintViolation<EnrollChannelRequest>> validate = validator.validate(enrollChannelRequest);

        // then
        assertThat(validate.isEmpty()).isFalse();
    }

    static Stream<String> validChannelEmail() {
        return Stream.of("valid@valid.com", "test@test.com");
    }

    @ParameterizedTest(name = "{index} - input channelEmail = {0}")
    @MethodSource("validChannelEmail")
    @DisplayName("채널 이메일에 유효한 값이 입력될 경우 검증이 통과됨")
    void EnrollChannelRequest_channelEmail_valid_then_EnrollChannelRequest_channelEmail_fail(String channelEmail) {
        // given
        EnrollChannelRequest enrollChannelRequest = new EnrollChannelRequest(channelName, channelEmail);

        // when
        Set<ConstraintViolation<EnrollChannelRequest>> validate = validator.validate(enrollChannelRequest);

        // then
        assertThat(validate.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("유효한 채널명과 이메일이 입력될 경우 검증이 통과됨")
    void EnrollChannelRequest_valid_then_EnrollChannelRequest_success() {
        // given
        EnrollChannelRequest enrollChannelRequest = new EnrollChannelRequest(channelName, channelEmail);

        // when
        Set<ConstraintViolation<EnrollChannelRequest>> validate = validator.validate(enrollChannelRequest);

        // then
        assertThat(validate.isEmpty()).isTrue();
    }
}
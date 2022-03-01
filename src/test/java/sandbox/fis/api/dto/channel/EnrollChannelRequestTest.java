package sandbox.fis.api.dto.channel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
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
        // givne
        EnrollChannelRequest EnrollChannelRequest = new EnrollChannelRequest(channelName);

        // when
        Set<ConstraintViolation<EnrollChannelRequest>> validate = validator.validate(EnrollChannelRequest);

        // then
        assertThat(validate.isEmpty()).isFalse();
    }

    static Stream<String> overLengthValue() {
        return Stream.of("채널명30자이상채널명30자이상채널명30자이상채널명30자이");
    }

    @ParameterizedTest(name = "{index} - input channelName = {0}")
    @MethodSource("overLengthValue")
    @DisplayName("채널명이 30자 이상 입력될 경우 검증이 통과되지 않음")
    void EnrollChannelRequest_channelName_length_over_then_EnrollChannelRequest_channelName_fail(String channelName) {
        // given
        EnrollChannelRequest EnrollChannelRequest = new EnrollChannelRequest(channelName);

        // when
        Set<ConstraintViolation<EnrollChannelRequest>> validate = validator.validate(EnrollChannelRequest);

        // then
        assertThat(validate.isEmpty()).isFalse();
    }

    static Stream<String> validValue() {
        return Stream.of("채널명", "채널입니다", "채널채널");
    }

    @ParameterizedTest(name = "{index} - input channelName = {0}")
    @MethodSource("validValue")
    @DisplayName("채널명에 유효한 값이 입력될 경우 검증이 통과됨")
    void EnrollChannelRequest_channelName_valid_then_EnrollChannelRequest_channelName_success(String channelName) {
        // given
        EnrollChannelRequest EnrollChannelRequest = new EnrollChannelRequest(channelName);

        // when
        Set<ConstraintViolation<EnrollChannelRequest>> validate = validator.validate(EnrollChannelRequest);

        // then
        assertThat(validate.isEmpty()).isTrue();
    }
}
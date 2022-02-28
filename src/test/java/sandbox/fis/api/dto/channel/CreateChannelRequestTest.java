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

@DisplayName("CreateChannelRequest 테스트")
class CreateChannelRequestTest {

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
    void CreateChannelRequest_channelName_blank_or_null_then_CreateChannelRequest_channelName_fail(String channelName) {
        // givne
        CreateChannelRequest createChannelRequest = new CreateChannelRequest(channelName);

        // when
        Set<ConstraintViolation<CreateChannelRequest>> validate = validator.validate(createChannelRequest);

        // then
        assertThat(validate.isEmpty()).isFalse();
    }
}
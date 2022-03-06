package sandbox.fis.api.service.channel;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import sandbox.fis.api.dto.channel.EnrollChannelRequest;
import sandbox.fis.api.dto.channel.EnrollChannelResponse;
import sandbox.fis.api.entity.channel.Channel;
import sandbox.fis.api.repository.channel.ChannelRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("ChannelService 테스트")
@ExtendWith(MockitoExtension.class)
class ChannelServiceTest {

    @InjectMocks ChannelService channelService;
    @Mock ChannelRepository channelRepository;

    // Channel 정보
    Channel channel;
    final Long channelId = 1L;
    final String channelName = "채널";
    final String channelEmail = "email@email.com";

    @BeforeEach
    void setUp() {
        channel = Channel.builder().channelName(channelName).build();
        ReflectionTestUtils.setField(channel, "id", channelId);
    }
    
    @Test
    @DisplayName("채널 등록시 이메일이 중복되면 IllegalArgumentException 발생")
    void enroll_channel_email_exists_then_throw_IllegalArgumentException() {
        // given
        EnrollChannelRequest enrollChannelRequest = new EnrollChannelRequest(channelName, channelEmail);

        // when
        when(channelRepository.existsByEmail(channelEmail)).thenReturn(true);

        // then
        assertThrows(IllegalArgumentException.class, () -> channelService.enroll(enrollChannelRequest));
    }
    
    @Test
    @DisplayName("채널 등록시 등록 성공")
    void enroll_channel_then_enroll_channel_success() {
        // given
        EnrollChannelRequest enrollChannelRequest = new EnrollChannelRequest(channelName, channelEmail);

        // when
        when(channelRepository.existsByEmail(channelEmail)).thenReturn(false);
        when(channelRepository.save(any(Channel.class))).thenReturn(channel);
        EnrollChannelResponse createChannelResponse = channelService.enroll(enrollChannelRequest);

        // then
        assertThat(createChannelResponse.getChannel_id()).isEqualTo(channelId);
    }
}
package sandbox.fis.api.service.channel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import sandbox.fis.api.dto.channel.CreateChannelRequest;
import sandbox.fis.api.dto.channel.CreateChannelResponse;
import sandbox.fis.api.entity.channel.Channel;
import sandbox.fis.api.repository.channel.ChannelRepository;

import static org.assertj.core.api.Assertions.*;
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

    @BeforeEach
    void setUp() {
        channel = Channel.builder().channelName(channelName).build();
        ReflectionTestUtils.setField(channel, "id", channelId);
    }
    
    @Test
    @DisplayName("채널 등록시 등록 성공")
    void save_channel_then_save_channel_success() {
        // given
        CreateChannelRequest createChannelRequest = new CreateChannelRequest(channelName);

        // when
        when(channelRepository.save(any(Channel.class))).thenReturn(channel);
        CreateChannelResponse createChannelResponse = channelService.create(createChannelRequest);

        // then
        assertThat(createChannelResponse.getChannel_id()).isEqualTo(channelId);
    }
}
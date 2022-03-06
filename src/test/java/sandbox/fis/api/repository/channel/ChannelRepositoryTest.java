package sandbox.fis.api.repository.channel;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sandbox.fis.api.entity.channel.Channel;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ChannelRepository 테스트")
@DataJpaTest
class ChannelRepositoryTest {

    @Autowired ChannelRepository channelRepository;

    final String email = "email@email.com";
    final String name = "channel";

    @BeforeEach
    void setUp() {
        channelRepository.save(Channel.builder().channelName(name).email(email).build());
    }

    @AfterEach
    void tearDown() {
        channelRepository.deleteAll();
    }
    
    @Test
    @DisplayName("이메일 존재시 true 반환")
    void channel_email_exists_then_return_true() {
        // when
        boolean result = channelRepository.existsByEmail(email);

        // then
        assertThat(result).isTrue();
    }
}
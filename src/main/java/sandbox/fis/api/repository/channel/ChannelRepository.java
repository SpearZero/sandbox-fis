package sandbox.fis.api.repository.channel;

import org.springframework.data.jpa.repository.JpaRepository;
import sandbox.fis.api.entity.channel.Channel;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
    Boolean existsByEmail(String email);
}

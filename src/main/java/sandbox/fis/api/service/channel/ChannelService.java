package sandbox.fis.api.service.channel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sandbox.fis.api.dto.channel.EnrollChannelRequest;
import sandbox.fis.api.dto.channel.EnrollChannelResponse;
import sandbox.fis.api.entity.channel.Channel;
import sandbox.fis.api.repository.channel.ChannelRepository;

@RequiredArgsConstructor
@Service
public class ChannelService {

    private final ChannelRepository channelRepository;

    public EnrollChannelResponse enroll(EnrollChannelRequest request) {
        if (channelRepository.existsByEmail(request.getChannel_email())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        Channel channel = channelRepository.save(request.toEntity());

        return new EnrollChannelResponse(channel.getId());
    }
}

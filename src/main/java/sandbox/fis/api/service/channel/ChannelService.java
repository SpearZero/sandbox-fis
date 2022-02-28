package sandbox.fis.api.service.channel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sandbox.fis.api.dto.channel.CreateChannelRequest;
import sandbox.fis.api.dto.channel.CreateChannelResponse;
import sandbox.fis.api.entity.channel.Channel;
import sandbox.fis.api.repository.channel.ChannelRepository;

@RequiredArgsConstructor
@Service
public class ChannelService {

    private final ChannelRepository channelRepository;

    public CreateChannelResponse create(CreateChannelRequest request) {
        Channel channel = channelRepository.save(request.toEntity());
        return new CreateChannelResponse(channel.getId());
    }
}

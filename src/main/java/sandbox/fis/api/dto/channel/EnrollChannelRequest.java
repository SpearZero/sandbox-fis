package sandbox.fis.api.dto.channel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import sandbox.fis.api.entity.channel.Channel;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollChannelRequest {

    @NotBlank
    @Length(max = 30)
    private String channel_name;

    public Channel toEntity() {
        return Channel.builder().channelName(this.channel_name).build();
    }
}

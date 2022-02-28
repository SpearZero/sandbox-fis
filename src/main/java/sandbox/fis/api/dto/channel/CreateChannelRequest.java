package sandbox.fis.api.dto.channel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateChannelRequest {

    @NotBlank
    @Length(max = 30)
    private String channel_name;
}

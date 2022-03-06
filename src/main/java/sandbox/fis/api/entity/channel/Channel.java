package sandbox.fis.api.entity.channel;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sandbox.fis.api.entity.BaseTimeEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Channel extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "channel_id")
    private Long id;

    @Column(length = 90, nullable = false)
    private String channelName;

    @Column(nullable = false, unique = true)
    private String email;

    @Builder
    public Channel(String channelName, String email) {
        this.channelName = channelName;
        this.email = email;
    }
}

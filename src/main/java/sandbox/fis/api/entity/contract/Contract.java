package sandbox.fis.api.entity.contract;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sandbox.fis.api.entity.BaseTimeEntity;
import sandbox.fis.api.entity.channel.Channel;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Contract extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @Column(nullable = false)
    private Integer companyRs;

    @Column(nullable = false)
    private Integer creatorRs;

    @Builder
    public Contract(Channel channel, Integer companyRs, Integer creatorRs) {
        this.channel = channel;
        this.companyRs = companyRs;
        this.creatorRs = creatorRs;
    }
}

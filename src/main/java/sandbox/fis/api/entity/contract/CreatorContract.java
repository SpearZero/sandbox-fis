package sandbox.fis.api.entity.contract;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sandbox.fis.api.entity.BaseTimeEntity;
import sandbox.fis.api.entity.creator.Creator;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CreatorContract extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "creator_contract_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @OneToOne
    @JoinColumn(name = "creator_id")
    private Creator creator;

    @Column(nullable = false)
    private Integer creatorRs;
}

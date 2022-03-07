package sandbox.fis.api.entity.amount;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sandbox.fis.api.entity.BaseTimeEntity;
import sandbox.fis.api.entity.contract.Contract;
import sandbox.fis.api.entity.creator.Creator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CreatorAmount extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "creator_amount_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Creator creator;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "amount_id")
    private Amount allAmount;

    @Column(nullable = false, precision = 22, scale = 7)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate day;

    @Builder
    public CreatorAmount(Contract contract, Creator creator, Amount allAmount, BigDecimal amount, LocalDate day) {
        this.contract = contract;
        this.creator = creator;
        this.allAmount = allAmount;
        this.amount = amount;
        this.day = day;
    }
}

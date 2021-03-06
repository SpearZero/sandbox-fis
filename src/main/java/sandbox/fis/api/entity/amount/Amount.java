package sandbox.fis.api.entity.amount;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sandbox.fis.api.entity.BaseTimeEntity;
import sandbox.fis.api.entity.contract.Contract;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Amount extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "amount_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    @Column(nullable = false, precision = 22, scale = 7)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate day;

    @Builder
    public Amount(Contract contract, BigDecimal amount, LocalDate day) {
        this.contract = contract;
        this.amount = amount;
        this.day = day;
    }
}

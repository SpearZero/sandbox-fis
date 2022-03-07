package sandbox.fis.api.dto.amount.list;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PerCreatorAmountsDto {

    // 계약서 ID
    private Long contract_id;

    // 크리에이터 ID
    private Long creator_id;

    // 크리에이터 합계 수입 금액
    List<PerCreatorAmountDto> creator_amounts;
}

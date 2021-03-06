package sandbox.fis.api.dto.amount.list;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreatorAmountsDto {

    // 계약서 ID
    private Long contract_id;

    // 크리에이터들 합계 수입 금액
    List<CreatorAmountDto> creators_amounts;
}

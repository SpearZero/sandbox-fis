package sandbox.fis.api.dto.amount.list;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatorAmountDto {

    // 크리에이터 금액 합계
    private String amount;

    // 월
    private String month;
}

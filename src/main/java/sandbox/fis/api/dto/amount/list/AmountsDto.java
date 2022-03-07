package sandbox.fis.api.dto.amount.list;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AmountsDto {

    private Long contract_id;
    private String month;

    private String total_amount;
    private String net_amount;
}

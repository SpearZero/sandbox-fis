package sandbox.fis.api.dto.contract;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContractCreatorRequest {

    @NotNull
    @Min(value = 1)
    private Long creator_id;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer creator_rs;
}

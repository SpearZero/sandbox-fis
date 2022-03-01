package sandbox.fis.api.dto.contract;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContractRequest {

    @Valid
    ContractChannelRequest company;

    @Valid
    List<ContractCreatorRequest> creators;
}

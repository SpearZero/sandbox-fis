package sandbox.fis.api.service.amount;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sandbox.fis.api.dto.amount.list.CreatorAmountDto;
import sandbox.fis.api.dto.amount.list.CreatorAmountsDto;
import sandbox.fis.api.dto.amount.list.PerCreatorAmountDto;
import sandbox.fis.api.dto.amount.list.PerCreatorAmountsDto;
import sandbox.fis.api.repository.amount.CreatorAmountRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AmountQueryService {

    private final CreatorAmountRepository creatorAmountRepository;

    public CreatorAmountsDto getCreatorAmounts(Long contractId, String startMonth, String endMonth) {
        List<CreatorAmountDto> creatorAmounts = creatorAmountRepository.findCreatorAmounts(contractId, startMonth, endMonth)
                .stream().map(creatorAmount -> new CreatorAmountDto(creatorAmount.get("amount", String.class),
                        creatorAmount.get("month", String.class))).collect(Collectors.toList());

        CreatorAmountsDto creatorAmountsDto = new CreatorAmountsDto(contractId, creatorAmounts);

        return creatorAmountsDto;
    }

    public PerCreatorAmountsDto getPerCreatorAmounts(Long contractId, Long creatorId, String startMonth, String endMonth) {
        List<PerCreatorAmountDto> perCreatorAmounts = creatorAmountRepository.findPerCreatorAmounts(contractId, creatorId, startMonth, endMonth)
                .stream().map(perCreatorAmount -> new PerCreatorAmountDto(perCreatorAmount.get("amount", String.class),
                        perCreatorAmount.get("month", String.class))).collect(Collectors.toList());

        PerCreatorAmountsDto perCreatorAmountsDto = new PerCreatorAmountsDto(contractId, creatorId, perCreatorAmounts);

        return perCreatorAmountsDto;
    }
}

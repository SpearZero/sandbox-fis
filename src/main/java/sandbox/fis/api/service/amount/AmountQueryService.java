package sandbox.fis.api.service.amount;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sandbox.fis.api.dto.amount.list.*;
import sandbox.fis.api.repository.amount.AmountQueryRepository;
import sandbox.fis.api.repository.amount.CreatorAmountQueryRepository;

import javax.persistence.Tuple;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AmountQueryService {

    private final CreatorAmountQueryRepository creatorAmountQueryRepository;
    private final AmountQueryRepository amountQueryRepository;

    // 채널별 크리에이터들의 정산금액 조회
    public CreatorAmountsDto getCreatorAmounts(Long contractId, String startMonth, String endMonth) {
        List<CreatorAmountDto> creatorAmounts = creatorAmountQueryRepository.findCreatorAmounts(contractId, startMonth, endMonth)
                .stream().map(creatorAmount -> new CreatorAmountDto(creatorAmount.get("amount", String.class),
                        creatorAmount.get("month", String.class))).collect(Collectors.toList());

        CreatorAmountsDto creatorAmountsDto = new CreatorAmountsDto(contractId, creatorAmounts);

        return creatorAmountsDto;
    }

    // 채널별 각 크레이터드의 정산금액 조회
    public PerCreatorAmountsDto getPerCreatorAmounts(Long contractId, Long creatorId, String startMonth, String endMonth) {
        List<PerCreatorAmountDto> perCreatorAmounts = creatorAmountQueryRepository.findPerCreatorAmounts(contractId, creatorId, startMonth, endMonth)
                .stream().map(perCreatorAmount -> new PerCreatorAmountDto(perCreatorAmount.get("amount", String.class),
                        perCreatorAmount.get("month", String.class))).collect(Collectors.toList());

        PerCreatorAmountsDto perCreatorAmountsDto = new PerCreatorAmountsDto(contractId, creatorId, perCreatorAmounts);

        return perCreatorAmountsDto;
    }

    // 채널별 총매출 / 순매출 조회
    public AmountsDto getAmounts(Long contractId, String month) {
            String companyTotalAmounts = amountQueryRepository.findCompanyTotalAmounts(contractId, month).get("amount", String.class);
        String companyNetAmounts = amountQueryRepository.findCompanyNetAmounts(contractId, month).get("amount", String.class);

        AmountsDto amountsDto = new AmountsDto(contractId, month, companyTotalAmounts, companyNetAmounts);

        return amountsDto;
    }
}

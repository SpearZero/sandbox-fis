package sandbox.fis.api.service.amount;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sandbox.fis.api.dto.amount.AmountRequest;
import sandbox.fis.api.dto.amount.AmountResponse;
import sandbox.fis.api.dto.amount.list.CreatorAmountDto;
import sandbox.fis.api.dto.amount.list.CreatorAmountsDto;
import sandbox.fis.api.dto.amount.list.PerCreatorAmountDto;
import sandbox.fis.api.dto.amount.list.PerCreatorAmountsDto;
import sandbox.fis.api.entity.amount.Amount;
import sandbox.fis.api.entity.amount.CompanyAmount;
import sandbox.fis.api.entity.amount.CreatorAmount;
import sandbox.fis.api.entity.contract.Contract;
import sandbox.fis.api.entity.contract.CreatorContract;
import sandbox.fis.api.entity.creator.Creator;
import sandbox.fis.api.repository.amount.AmountRepository;
import sandbox.fis.api.repository.amount.CompanyAmountRepository;
import sandbox.fis.api.repository.amount.CreatorAmountRepository;
import sandbox.fis.api.repository.contract.ContractRepository;
import sandbox.fis.api.util.AmountCalculator;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class AmountService {

    private final ContractRepository contractRepository;
    private final AmountRepository amountRepository;
    private final CompanyAmountRepository companyAmountRepository;
    private final CreatorAmountRepository creatorAmountRepository;
    private final AmountCalculator amountCalculator;

    // 계약서 저장
    public AmountResponse saveAmount(AmountRequest amountRequest) {

        // 계약서 조회
        Contract contract = contractRepository.findById(amountRequest.getContract_id())
                .orElseThrow(() -> new IllegalArgumentException("계약서 ID가 존재하지 않습니다."));

        // 입력 금액, 입력 날짜
        BigDecimal inputAmount = amountRequest.getAmount().setScale(7);
        LocalDate day = amountRequest.getDay();

        // 총 금액 저장
        Amount amount = amountRepository.save(Amount.builder()
                .contract(contract).amount(inputAmount).day(day).build());

        // 금액 계산(회사, 크리에이터)
        BigDecimal calculatedCompanyAmount = amountCalculator.calculateAmount(inputAmount, contract.getCompanyRs());
        BigDecimal calculatedCreatorsAmount = amountCalculator.calculateAmount(inputAmount, contract.getCreatorRs());

        // 회사 금액 저장
        companyAmountRepository.save(CompanyAmount.builder()
                .contract(contract).allAmount(amount).amount(calculatedCompanyAmount).day(day).build());

        // 크리에이터 금액 저장
        List<CreatorContract> creatorsContract = contract.getCreatorsContract();
        for (CreatorContract creatorContract : creatorsContract) {
            Creator creator = creatorContract.getCreator();

            BigDecimal calculatedCreatorAmount = amountCalculator.calculateCreatorsAmount(calculatedCreatorsAmount, creatorContract.getCreatorRs());

            creatorAmountRepository.save(CreatorAmount.builder()
                    .contract(contract).creator(creator).allAmount(amount).amount(calculatedCreatorAmount)
                    .day(day).build());
        }

        return new AmountResponse(contract.getId());
    }
}

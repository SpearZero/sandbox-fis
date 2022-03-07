package sandbox.fis.api.service.contract;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sandbox.fis.api.dto.contract.ContractCompanyRequest;
import sandbox.fis.api.dto.contract.ContractCreatorRequest;
import sandbox.fis.api.dto.contract.ContractRequest;
import sandbox.fis.api.dto.contract.ContractResponse;
import sandbox.fis.api.entity.channel.Channel;
import sandbox.fis.api.entity.contract.Contract;
import sandbox.fis.api.entity.contract.CreatorContract;
import sandbox.fis.api.entity.creator.Creator;
import sandbox.fis.api.repository.channel.ChannelRepository;
import sandbox.fis.api.repository.contract.ContractRepository;
import sandbox.fis.api.repository.contract.CreatorContractRepository;
import sandbox.fis.api.repository.creator.CreatorRepository;
import sandbox.fis.api.util.RsCalculator;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class ContractService {

    private final ChannelRepository channelRepository;
    private final CreatorRepository creatorRepository;
    private final ContractRepository contractRepository;
    private final CreatorContractRepository creatorContractRepository;

    private final RsCalculator rsCalculator;

    // 계약서 등록
    public ContractResponse makeContract(ContractRequest contractRequest) {
        // 계약서 요청값이 존재하는지 검증
        existsByContract(contractRequest);

        ContractCompanyRequest contractCompanyRequest = contractRequest.getCompany();
        List<ContractCreatorRequest> contractCreatorsRequest = contractRequest.getCreators();
        
        // 회사와 크리에이터 RS의 합 검증
        rsContractSumValid(contractCompanyRequest.getCompany_rs(), contractCompanyRequest.getCreators_rs());

        // 크리에이터간 RS의 합 검증
        List<Integer> creatorsRs = contractCreatorsRequest.stream().map(creator -> creator.getCreator_rs()).collect(Collectors.toList());
        rsContractCreatorsSumValid(creatorsRs);

        // 채널 조회
        Channel channel = channelRepository.findById(contractCompanyRequest.getChannel_id())
                .orElseThrow(() -> new IllegalArgumentException("채널이 존재하지 않습니다."));

        // 계약서 생성(회사)
        Contract contract = contractRepository.save(Contract.builder().channel(channel).companyRs(contractCompanyRequest.getCompany_rs())
                .creatorRs(contractCompanyRequest.getCreators_rs()).build());
        
        // 계약서 생성(크리에이터)
        for (ContractCreatorRequest contractCreatorRequest : contractCreatorsRequest) {
            Creator creator = creatorRepository.findById(contractCreatorRequest.getCreator_id())
                    .orElseThrow(() -> new IllegalArgumentException("크리에이터가 존재하지 않습니다."));

            creatorContractRepository.save(CreatorContract.builder().contract(contract)
                    .creator(creator).creatorRs(contractCreatorRequest.getCreator_rs()).build());
        }

        return new ContractResponse(contract.getId());
    }

    private void existsByContract(ContractRequest contractRequest) {
        if (null == contractRequest.getCompany()) {
            throw new IllegalArgumentException("회사 계약 정보가 존재하지 않습니다.");
        }

        if (null == contractRequest.getCreators() || 0 == contractRequest.getCreators().size()) {
            throw new IllegalArgumentException("크리에이터 계약 정보가 존재하지 않습니다.");
        }
    }

    private void rsContractSumValid(Integer companyRs, Integer creatorRs) {
        if (!rsCalculator.rsContractSumValid(companyRs, creatorRs)) {
            throw new IllegalArgumentException("RS의 합이 유효하지 않습니다.(회사, 크리에이터)");
        }
    }

    private void rsContractCreatorsSumValid(List<Integer> creatorsRs) {
        if (!rsCalculator.rsContractCreatorsSumValid(creatorsRs)) {
            throw new IllegalArgumentException("RS의 합이 유효하지 않습니다.(크리에이터)");
        }
    }
}

package sandbox.fis.api.service.contract;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import sandbox.fis.api.dto.contract.ContractCompanyRequest;
import sandbox.fis.api.dto.contract.ContractCreatorRequest;
import sandbox.fis.api.dto.contract.ContractRequest;
import sandbox.fis.api.dto.contract.ContractResponse;
import sandbox.fis.api.entity.channel.Channel;
import sandbox.fis.api.entity.contract.Contract;
import sandbox.fis.api.entity.creator.Creator;
import sandbox.fis.api.repository.channel.ChannelRepository;
import sandbox.fis.api.repository.contract.ContractRepository;
import sandbox.fis.api.repository.contract.CreatorContractRepository;
import sandbox.fis.api.repository.creator.CreatorRepository;
import sandbox.fis.api.util.RsCalculator;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("ContractService 테스트")
@ExtendWith(MockitoExtension.class)
class ContractServiceTest {

    @InjectMocks ContractService contractService;
    @Mock ChannelRepository channelRepository;
    @Mock CreatorRepository creatorRepository;
    @Mock ContractRepository contractRepository;
    @Mock CreatorContractRepository creatorContractRepository;
    @Mock RsCalculator rsCalculator;

    final Long contractId = 1L;

    final Long channelId = 1L;
    final Integer companyRs = 50;
    final Integer creatorRs = 50;
    final String channelName = "channelName";

    final Long creatorId1 = 1L;
    final Long creatorId2 = 2L;

    final String creatorName1 = "creatorName";
    final String creatorEmail1 = "email@email.com";

    final String creatorName2 = "creatorName2";
    final String creatorEmail2 = "email2@eamail.com";

    final Integer creatorRs1 = 50;
    final Integer creatorRs2 = 50;

    ContractCompanyRequest contractCompanyRequest;
    List<ContractCreatorRequest> contractCreatorsRequest;

    // invalid
    final Long invalidChannelId = 2L;
    final Integer invalidCompanyRs = 55;
    final Integer invalidCreatorRs = 40;

    final Long invalidCreatorId1 = 3L;
    final Long invalidCreatorId2 = 4L;

    final Integer invalidCreatorRs1 = 50;
    final Integer invalidCreatorRs2 = 45;

    ContractCompanyRequest invalidContractCompanyRequest;
    List<ContractCreatorRequest> invalidContractCreatorsRequest;
    Contract contract;
    Channel channel;
    Creator creator1;
    Creator creator2;

    @BeforeEach
    void setUp() {
        contractCompanyRequest = new ContractCompanyRequest(channelId, companyRs, creatorRs);
        contractCreatorsRequest = Arrays.asList(
                new ContractCreatorRequest(creatorId1, creatorRs1),
                new ContractCreatorRequest(creatorId2, creatorRs2)).stream().collect(Collectors.toList());

        invalidContractCompanyRequest = new ContractCompanyRequest(invalidChannelId, invalidCompanyRs, invalidCreatorRs);
        invalidContractCreatorsRequest = Arrays.asList(
                new ContractCreatorRequest(invalidCreatorId1, invalidCreatorRs1),
                new ContractCreatorRequest(invalidCreatorId2, invalidCreatorRs2)).stream().collect(Collectors.toList());

        channel = Channel.builder().channelName(channelName).build();
        ReflectionTestUtils.setField(channel, "id", channelId);

        creator1 = Creator.builder().email(creatorEmail1).name(creatorName1).build();
        ReflectionTestUtils.setField(creator1, "id", creatorId1);

        creator2 = Creator.builder().email(creatorEmail2).name(creatorName2).build();
        ReflectionTestUtils.setField(creator2, "id", creatorId2);

        contract = Contract.builder().companyRs(companyRs).creatorRs(creatorRs).channel(channel).build();
        ReflectionTestUtils.setField(contract, "id", contractId);
    }

    @Test
    @DisplayName("회사 계약 정보가 존재하지 않으면 IllegalArgumentException 발생")
    void ContractRequest_company_null_then_throw_IllegalArgumentException() {
        // given
        ContractRequest contractRequest = new ContractRequest(null, contractCreatorsRequest);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> contractService.makeContract(contractRequest));
    }
    
    @Test
    @DisplayName("크리에이터 계약 정보가 존재하지 않으면 IllegalArgumentException 발생")
    void ContractRequest_creator_null_then_throw_IllegalArgumentException() {
        // given
        ContractRequest contractRequest = new ContractRequest(contractCompanyRequest, null);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> contractService.makeContract(contractRequest));
    }

    @Test
    @DisplayName("회사, 크리에이터 RS합이 유효하지 않으면 IllegalArgumentException 발생")
    void ContractRequest_company_creator_rs_sum_not_valid_then_throw_IllegalArgumentException() {
        // given
        ContractRequest contractRequest = new ContractRequest(invalidContractCompanyRequest, invalidContractCreatorsRequest);

        // when
        when(rsCalculator.rsContractSumValid(any(Integer.class), any(Integer.class))).thenReturn(false);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> contractService.makeContract(contractRequest));
    }
    
    @Test
    @DisplayName("크리에이터들의 RS합이 유효하지 않으면 IllegalArgumentException 발생")
    void ContractRequest_creators_rs_sum_not_valid_then_throw_IllegalArgumentException() {
        // given
        ContractRequest contractRequest = new ContractRequest(contractCompanyRequest, invalidContractCreatorsRequest);

        // when
        when(rsCalculator.rsContractSumValid(any(Integer.class), any(Integer.class))).thenReturn(true);
        when(rsCalculator.rsContractCreatorsSumValid(any(List.class))).thenReturn(false);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> contractService.makeContract(contractRequest));
    }
    
    @Test
    @DisplayName("채널이 존재하지 않으면 IllegalArgumentException 발생")
    void ContractRequest_channel_not_exists_then_throw_IllegalArgumentException() {
        // given
        ContractRequest contractRequest = new ContractRequest(contractCompanyRequest, contractCreatorsRequest);

        // when
        when(rsCalculator.rsContractSumValid(anyInt(), anyInt())).thenReturn(true);
        when(rsCalculator.rsContractCreatorsSumValid(any(List.class))).thenReturn(true);
        when(channelRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        // then
        assertThrows(IllegalArgumentException.class, () -> contractService.makeContract(contractRequest));
    }
    
    @Test
    @DisplayName("크리에이터가 존재하지 않으면 IllegalArgumentException 발생")
    void ContractRequest_creator_not_exists_then_throw_IllegalArgumentException() {
        // given
        ContractRequest contractRequest = new ContractRequest(contractCompanyRequest, contractCreatorsRequest);

        // when
        when(rsCalculator.rsContractSumValid(anyInt(), anyInt())).thenReturn(true);
        when(rsCalculator.rsContractCreatorsSumValid(any(List.class))).thenReturn(true);
        when(channelRepository.findById(any(Long.class))).thenReturn(Optional.of(channel));
        when(creatorRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        // then
        assertThrows(IllegalArgumentException.class, () -> contractService.makeContract(contractRequest));
    }
    
    @Test
    @DisplayName("계약서 작성시 계약 성공")
    void ContractRequest_contract_then_contract_success() {
        // given
        ContractRequest contractRequest = new ContractRequest(contractCompanyRequest, contractCreatorsRequest);

        // when
        when(rsCalculator.rsContractSumValid(anyInt(), anyInt())).thenReturn(true);
        when(rsCalculator.rsContractCreatorsSumValid(any(List.class))).thenReturn(true);
        when(contractRepository.save(any(Contract.class))).thenReturn(contract);
        when(channelRepository.findById(any(Long.class))).thenReturn(Optional.of(channel));
        when(creatorRepository.findById(any(Long.class))).thenReturn(Optional.of(creator1), Optional.of(creator2));

        // then
        ContractResponse contractResponse = contractService.makeContract(contractRequest);
        assertThat(contractResponse.getContract_id()).isEqualTo(contractId);
    }
}
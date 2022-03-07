package sandbox.fis.api.service.amount;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import sandbox.fis.api.dto.amount.AmountRequest;
import sandbox.fis.api.dto.amount.AmountResponse;
import sandbox.fis.api.entity.amount.Amount;
import sandbox.fis.api.entity.amount.CompanyAmount;
import sandbox.fis.api.entity.amount.CreatorAmount;
import sandbox.fis.api.entity.channel.Channel;
import sandbox.fis.api.entity.contract.Contract;
import sandbox.fis.api.entity.contract.CreatorContract;
import sandbox.fis.api.entity.creator.Creator;
import sandbox.fis.api.repository.amount.AmountRepository;
import sandbox.fis.api.repository.amount.CompanyAmountRepository;
import sandbox.fis.api.repository.amount.CreatorAmountRepository;
import sandbox.fis.api.repository.contract.ContractRepository;
import sandbox.fis.api.util.AmountCalculator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("AmountService 테스트")
@ExtendWith(MockitoExtension.class)
class AmountServiceTest {

    @InjectMocks AmountService amountService;
    @Mock ContractRepository contractRepository;
    @Mock AmountRepository amountRepository;
    @Mock CompanyAmountRepository companyAmountRepository;
    @Mock CreatorAmountRepository creatorAmountRepository;
    @Mock AmountCalculator amountCalculator;

    final LocalDate day = LocalDate.of(2022, 3, 7);

    Contract contract;
    final Long contractId = 1L;
    final Long notExistsContractId = 2L;
    final Integer companyRs = 50;
    final Integer creatorRs = 50;

    Creator creator1;
    Creator creator2;
    final Long creatorId1 = 1L;
    final Long creatorId2 = 2L;
    final String creatorName1 = "name1";
    final String creatorName2 = "name2";
    final String creatorEmail1 = "creator1@eamil.com";
    final String creatorEmail2 = "creator2@eamil.com";

    CreatorContract creatorContract1;
    CreatorContract creatorContract2;
    final Long creatorContractId1 = 1L;
    final Long creatorContractId2 = 2L;
    final Integer creatorContractRs1 = 50;
    final Integer creatorContractRs2 = 50;

    Channel channel;
    final Long channelId = 1L;
    final String channelName = "channel";
    final String channelEmail = "email@eamil.com";

    Amount amount;
    final BigDecimal inputAmount = new BigDecimal("393939.35");
    final Long amountId = 1L;

    CompanyAmount companyAmount;
    final BigDecimal calculatedCompanyAmount = new BigDecimal("196969.6750000");
    final Long companyAmountId = 1L;

    CreatorAmount creatorAmount1;
    CreatorAmount creatorAmount2;
    final Long creatorAmountId1 = 1L;
    final Long creatorAmountId2 = 2L;
    final BigDecimal calculatedCreatorsAmount = new BigDecimal("196969.6750000");
    final BigDecimal calculatedCreatorAmount1 = new BigDecimal("98484.8375000");
    final BigDecimal calculatedCreatorAmount2 = new BigDecimal("98484.8375000");

    @BeforeEach
    void setUp() {
        channel = Channel.builder().channelName(channelName).email(channelEmail).build();
        ReflectionTestUtils.setField(channel, "id", channelId);

        creator1 = Creator.builder().email(creatorEmail1).name(creatorName1).build();
        ReflectionTestUtils.setField(creator1, "id", creatorId1);
        creator2 = Creator.builder().email(creatorEmail2).name(creatorName2).build();
        ReflectionTestUtils.setField(creator2, "id", creatorId2);

        contract = Contract.builder().channel(channel).companyRs(companyRs).creatorRs(creatorRs).build();
        ReflectionTestUtils.setField(contract, "id", contractId);

        creatorContract1 = CreatorContract.builder().contract(contract).creator(creator1).creatorRs(creatorContractRs1).build();
        ReflectionTestUtils.setField(creatorContract1, "id", creatorContractId1);
        creatorContract2 = CreatorContract.builder().contract(contract).creator(creator2).creatorRs(creatorContractRs2).build();
        ReflectionTestUtils.setField(creatorContract2, "id", creatorContractId2);

        ReflectionTestUtils.setField(contract, "creatorsContract", List.of(creatorContract1, creatorContract2));

        amount = Amount.builder().contract(contract).amount(inputAmount.setScale(7)).day(day).build();
        ReflectionTestUtils.setField(amount, "id", amountId);

        companyAmount = CompanyAmount.builder().contract(contract).allAmount(amount).amount(calculatedCompanyAmount).day(day).build();
        ReflectionTestUtils.setField(companyAmount, "id", companyAmountId);

        creatorAmount1 = CreatorAmount.builder().contract(contract).creator(creator1).allAmount(amount).amount(calculatedCreatorAmount1).day(day).build();
        ReflectionTestUtils.setField(creatorAmount1, "id", creatorAmountId1);
        creatorAmount2 = CreatorAmount.builder().contract(contract).creator(creator2).allAmount(amount).amount(calculatedCreatorAmount2).day(day).build();
        ReflectionTestUtils.setField(creatorAmount2, "id", creatorAmountId2);
    }

    @Test
    @DisplayName("계약서가 존재하지 않으면 IllegalArgumentException 발생")
    void AmountRequest_contract_not_exists_then_throw_IllegalArgumentException() {
        // given
        AmountRequest amountRequest = new AmountRequest(notExistsContractId, day, inputAmount);

        // when
        when(contractRepository.findById(notExistsContractId)).thenReturn(Optional.empty());

        // then
        assertThrows(IllegalArgumentException.class, () -> amountService.saveAmount(amountRequest));
    }
    
    @Test
    @DisplayName("금액정보 입력시 입력 성공")
    void AmountRequest_amount_then_amount_success() {
        // given
        AmountRequest amountRequest = new AmountRequest(contractId, day, inputAmount);

        // when
        when(contractRepository.findById(contractId)).thenReturn(Optional.of(contract));
        when(amountRepository.save(any(Amount.class))).thenReturn(amount);
        when(amountCalculator.calculateAmount(inputAmount.setScale(7), companyRs)).thenReturn(calculatedCompanyAmount);
        when(amountCalculator.calculateAmount(inputAmount.setScale(7), creatorRs)).thenReturn(calculatedCreatorsAmount);
        when(companyAmountRepository.save(any(CompanyAmount.class))).thenReturn(companyAmount);
        when(amountCalculator.calculateCreatorsAmount(calculatedCreatorsAmount, creatorContractRs1)).thenReturn(calculatedCreatorAmount1);
        when(amountCalculator.calculateCreatorsAmount(calculatedCreatorsAmount, creatorContractRs2)).thenReturn(calculatedCreatorAmount2);
        when(creatorAmountRepository.save(any(CreatorAmount.class))).thenReturn(creatorAmount1, creatorAmount2);

        // then
        AmountResponse amountResponse = amountService.saveAmount(amountRequest);
        assertThat(amountResponse.getContract_id()).isEqualTo(contractId);
    }
}
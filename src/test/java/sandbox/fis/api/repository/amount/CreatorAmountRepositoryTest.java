/*
package sandbox.fis.api.repository.amount;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sandbox.fis.api.entity.amount.Amount;
import sandbox.fis.api.entity.amount.CompanyAmount;
import sandbox.fis.api.entity.amount.CreatorAmount;
import sandbox.fis.api.entity.channel.Channel;
import sandbox.fis.api.entity.contract.Contract;
import sandbox.fis.api.entity.contract.CreatorContract;
import sandbox.fis.api.entity.creator.Creator;
import sandbox.fis.api.repository.channel.ChannelRepository;
import sandbox.fis.api.repository.contract.ContractRepository;
import sandbox.fis.api.repository.contract.CreatorContractRepository;
import sandbox.fis.api.repository.creator.CreatorRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

@DisplayName("CreatorAmountRepository 테스트")
@DataJpaTest
class CreatorAmountRepositoryTest {

    @Autowired ChannelRepository channelRepository;
    @Autowired CreatorRepository creatorRepository;
    @Autowired ContractRepository contractRepository;
    @Autowired CreatorContractRepository creatorContractRepository;

    @Autowired AmountRepository amountRepository;
    @Autowired CreatorAmountRepository creatorAmountRepository;
    @Autowired CompanyAmountRepository companyAmountRepository;

    final String channelName = "channel";
    final String channelEmail = "email@eamil.com";

    final String creatorName1 = "creator1";
    final String creatorName2 = "creator2";
    final String creatorEmail1 = "email1@email.com";
    final String creatorEmail2 = "email2@email.com";

    final Integer companyRs = 50;
    final Integer creatorRs = 50;
    final Integer creatorRs1 = 50;
    final Integer creatorRs2 = 50;

    final BigDecimal allAmount = new BigDecimal("12442.1100000");
    final BigDecimal companyAmount = new BigDecimal("6221.0550000");
    final BigDecimal creatorAmount = new BigDecimal("3110.5275000");

    final LocalDate day1 = LocalDate.of(2022,3,7);
    final LocalDate day2 = LocalDate.of(2022,3,8);
    final LocalDate day3 = LocalDate.of(2022,4,1);
    final LocalDate day4 = LocalDate.of(2022,4,2);
    final LocalDate day5 = LocalDate.of(2022,4,3);

    @BeforeEach
    void setUp() {
        Channel channel = channelRepository.save(Channel.builder().channelName(channelName).email(channelEmail).build());

        Creator creator1 = creatorRepository.save(Creator.builder().name(creatorName1).email(creatorEmail1).build());
        Creator creator2 = creatorRepository.save(Creator.builder().name(creatorName2).email(creatorEmail2).build());

        Contract contract = contractRepository.save(Contract.builder()
                .companyRs(companyRs).creatorRs(creatorRs).channel(channel).build());

        CreatorContract creatorContract1 = creatorContractRepository.save(CreatorContract.builder()
                .creator(creator1).creatorRs(creatorRs1).contract(contract).build());
        CreatorContract creatorContract2 = creatorContractRepository.save(CreatorContract.builder()
                .creator(creator2).creatorRs(creatorRs2).contract(contract).build());

        Amount amount1 = amountRepository.save(Amount.builder().amount(allAmount).day(day1).contract(contract).build());
        Amount amount2 = amountRepository.save(Amount.builder().amount(allAmount).day(day2).contract(contract).build());
        Amount amount3 = amountRepository.save(Amount.builder().amount(allAmount).day(day3).contract(contract).build());
        Amount amount4 = amountRepository.save(Amount.builder().amount(allAmount).day(day4).contract(contract).build());
        Amount amount5 = amountRepository.save(Amount.builder().amount(allAmount).day(day5).contract(contract).build());

        CompanyAmount companyAmount1 = companyAmountRepository.save(CompanyAmount.builder().allAmount(amount1).amount(companyAmount).day(day1).contract(contract).build());
        CompanyAmount companyAmount2 = companyAmountRepository.save(CompanyAmount.builder().allAmount(amount2).amount(companyAmount).day(day2).contract(contract).build());
        CompanyAmount companyAmount3 = companyAmountRepository.save(CompanyAmount.builder().allAmount(amount3).amount(companyAmount).day(day3).contract(contract).build());
        CompanyAmount companyAmount4 = companyAmountRepository.save(CompanyAmount.builder().allAmount(amount4).amount(companyAmount).day(day4).contract(contract).build());
        CompanyAmount companyAmount5 = companyAmountRepository.save(CompanyAmount.builder().allAmount(amount5).amount(companyAmount).day(day5).contract(contract).build());

        CreatorAmount creatorAmount11 = creatorAmountRepository.save(CreatorAmount.builder().allAmount(amount1).amount(creatorAmount).day(day1).creator(creator1).contract(contract).build());
        CreatorAmount creatorAmount12 = creatorAmountRepository.save(CreatorAmount.builder().allAmount(amount2).amount(creatorAmount).day(day2).creator(creator1).contract(contract).build());
        CreatorAmount creatorAmount13 = creatorAmountRepository.save(CreatorAmount.builder().allAmount(amount3).amount(creatorAmount).day(day3).creator(creator1).contract(contract).build());
        CreatorAmount creatorAmount14 = creatorAmountRepository.save(CreatorAmount.builder().allAmount(amount4).amount(creatorAmount).day(day4).creator(creator1).contract(contract).build());
        CreatorAmount creatorAmount15 = creatorAmountRepository.save(CreatorAmount.builder().allAmount(amount5).amount(creatorAmount).day(day5).creator(creator1).contract(contract).build());

        CreatorAmount creatorAmount21 = creatorAmountRepository.save(CreatorAmount.builder().allAmount(amount1).amount(creatorAmount).day(day1).creator(creator2).contract(contract).build());
        CreatorAmount creatorAmount22 = creatorAmountRepository.save(CreatorAmount.builder().allAmount(amount2).amount(creatorAmount).day(day2).creator(creator2).contract(contract).build());
        CreatorAmount creatorAmount23 = creatorAmountRepository.save(CreatorAmount.builder().allAmount(amount3).amount(creatorAmount).day(day3).creator(creator2).contract(contract).build());
        CreatorAmount creatorAmount24 = creatorAmountRepository.save(CreatorAmount.builder().allAmount(amount4).amount(creatorAmount).day(day4).creator(creator2).contract(contract).build());
        CreatorAmount creatorAmount25 = creatorAmountRepository.save(CreatorAmount.builder().allAmount(amount5).amount(creatorAmount).day(day5).creator(creator2).contract(contract).build());
    }

    @AfterEach
    void tearDown() {
        creatorAmountRepository.deleteAll();
        amountRepository.deleteAll();
        companyAmountRepository.deleteAll();

        creatorContractRepository.deleteAll();
        contractRepository.deleteAll();
        creatorRepository.deleteAll();
        channelRepository.deleteAll();
    }
}
 */
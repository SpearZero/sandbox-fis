package sandbox.fis.api.util;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RsCalculatorImpl implements RsCalculator {

    @Override
    public boolean rsContractSumValid(Integer companyRs, Integer creatorRs) {
        return RS_VALID_SUM == (companyRs + creatorRs);
    }

    @Override
    public boolean rsContractCreatorsSumValid(List<Integer> creatorsRs) {
        Integer rsSum = creatorsRs.stream().reduce(0, Integer::sum);
        return RS_VALID_SUM == rsSum;
    }
}

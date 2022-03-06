package sandbox.fis.api.util;

import java.util.List;

public interface RsCalculator {

    int RS_VALID_SUM = 100;

    boolean rsContractSumValid(Integer companyRs, Integer creatorRs);
    boolean rsContractCreatorsSumValid(List<Integer> creatorsRs);
}

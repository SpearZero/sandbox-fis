package sandbox.fis.api.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AmountCalculatorImpl implements AmountCalculator {

    @Override
    public BigDecimal calculateAmount(BigDecimal amount, Integer rate) {
        double calculatedRate = (double)rate / AMOUNT_RATE_DIVIDE;
        BigDecimal calculatedAmount = amount.multiply(BigDecimal.valueOf(calculatedRate)).setScale(7, RoundingMode.HALF_UP);
        return calculatedAmount;
    }

    @Override
    public BigDecimal calculateCreatorsAmount(BigDecimal creatorsAmount, Integer creatorRate) {
        double calculatedRate = ((double)creatorRate / AMOUNT_RATE_DIVIDE);
        BigDecimal calculatedAmount = creatorsAmount.multiply(BigDecimal.valueOf(calculatedRate)).setScale(7, RoundingMode.HALF_UP);
        return calculatedAmount;
    }
}

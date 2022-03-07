package sandbox.fis.api.util;

import java.math.BigDecimal;

public interface AmountCalculator {

    int AMOUNT_RATE_DIVIDE = 100;

    BigDecimal calculateAmount(BigDecimal amount, Integer rate);

    BigDecimal calculateCreatorsAmount(BigDecimal creatorsAmount, Integer creatorRate);
}

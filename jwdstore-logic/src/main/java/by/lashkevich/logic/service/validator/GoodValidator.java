package by.lashkevich.logic.service.validator;

import by.lashkevich.logic.entity.Good;

import java.math.BigDecimal;
import java.util.function.Predicate;

public class GoodValidator implements Predicate<Good> {
    private static final BigDecimal MAX_PRICE_VALUE = new BigDecimal("100000");
    private static final BigDecimal MIN_PRICE_VALUE = new BigDecimal("1");

    @Override
    public boolean test(Good good) {
        return good != null && validateGoodName(good.getName()) && validateGoodPrice(good.getPrice())
                && validateGoodDescription(good.getDescription()) && validateGoodType(good.getType())
                && good.getImgName() != null;
    }

    private boolean validateGoodType(String type) {
        return type != null && type.length() > 1 && type.length() < 20;
    }

    private boolean validateGoodDescription(String description) {
        return description != null && description.length() > 10 && description.length() < 400;
    }

    private boolean validateGoodName(String goodName) {
        return goodName != null && goodName.length() > 2 && goodName.length() < 60;
    }

    private boolean validateGoodPrice(BigDecimal price) {
        return price != null && price.compareTo(MAX_PRICE_VALUE) < 1 && price.compareTo(MIN_PRICE_VALUE) > -1;
    }
}

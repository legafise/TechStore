package by.lashkevich.logic.service.validator;

import by.lashkevich.logic.entity.Good;
import by.lashkevich.logic.entity.GoodType;
import by.lashkevich.logic.service.checker.FileFormatChecker;

import java.math.BigDecimal;

/**
 * The type Good validator.
 * @author Roman Lashkevich
 */
public class GoodValidator {
    private static final BigDecimal MAX_PRICE_VALUE = new BigDecimal("100000");
    private static final BigDecimal MIN_PRICE_VALUE = new BigDecimal("1");
    private FileFormatChecker formatChecker;

    public GoodValidator() {
        formatChecker = new FileFormatChecker();
    }

    public void setFormatChecker(FileFormatChecker formatChecker) {
        this.formatChecker = formatChecker;
    }

    public boolean validate(Good good) {
        return good != null && validateGoodName(good.getName()) && validateGoodPrice(good.getPrice())
                && validateGoodDescription(good.getDescription()) && validateGoodType(good.getType())
                && validateImgName(good.getImgName());
    }

    private boolean validateImgName(String imgName) {
        return imgName != null && formatChecker.checkImgFormat(imgName);
    }

    private boolean validateGoodType(GoodType type) {
        return type != null && type.getName().length() > 1 && type.getName().length() < 20;
    }

    private boolean validateGoodDescription(String description) {
        return description != null && description.length() > 10 && description.length() <= 600;
    }

    private boolean validateGoodName(String goodName) {
        return goodName != null && goodName.length() > 2 && goodName.length() < 60;
    }

    private boolean validateGoodPrice(BigDecimal price) {
        return price != null && price.compareTo(MAX_PRICE_VALUE) < 1 && price.compareTo(MIN_PRICE_VALUE) > -1;
    }
}

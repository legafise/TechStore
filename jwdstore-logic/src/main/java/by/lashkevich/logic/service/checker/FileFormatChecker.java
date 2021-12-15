package by.lashkevich.logic.service.checker;

import java.util.regex.Pattern;

/**
 * The type File format checker.
 */
public class FileFormatChecker {
    private static final String IMG_PATTERN = ".+([.](jpg|gif|png|bmp))";

    /**
     * Check img format boolean.
     *
     * @param imgFileName the img file name
     * @return the boolean
     */
    public boolean checkImgFormat(String imgFileName) {
        return Pattern.matches(IMG_PATTERN, imgFileName);
    }
}

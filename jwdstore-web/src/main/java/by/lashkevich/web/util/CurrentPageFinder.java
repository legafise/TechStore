package by.lashkevich.web.util;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CurrentPageFinder {
    private static final Pattern URL_PATTERN = Pattern.compile("/controller+.*");

    private CurrentPageFinder() {
    }

    public static String findCurrentPage(HttpServletRequest request) {
        Matcher matcher = URL_PATTERN.matcher(request.getHeader("referer"));
        matcher.find();
        return matcher.group();
    }
}

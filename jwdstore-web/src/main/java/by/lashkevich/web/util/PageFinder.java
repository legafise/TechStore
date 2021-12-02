package by.lashkevich.web.util;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PageFinder {
    private static final Pattern URL_PATTERN = Pattern.compile("/controller+.*");

    private PageFinder() {
    }

    public static String findLastPage(HttpServletRequest request) {
        Matcher matcher = URL_PATTERN.matcher(request.getHeader("referer"));
        matcher.find();
        return matcher.group();
    }
}

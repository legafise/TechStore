package by.lashkevich.web.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Ban filter.
 * @author Roman Lashkevich
 */
@WebFilter(filterName = "BanFilter")
public class BanFilter implements Filter {
    private static final String BANNED_ROLE = "banned";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (String.valueOf(httpRequest.getSession().getAttribute("role")).equals(BANNED_ROLE)
                && !AccessLevels.isAccessibleCommand(httpRequest)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/controller?command=ban_page");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}

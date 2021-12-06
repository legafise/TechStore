package by.lashkevich.web.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Page access filter.
 * @author Roman Lashkevich
 */
@WebFilter(filterName = "PageAccessFilter")
public class PageAccessFilter implements Filter {
    private static final String UNKNOWN_ACTION_MESSAGE = "Unknown action!";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (!AccessLevels.isAccessibleCommand(httpRequest)) {
            httpRequest.getSession().setAttribute("errorMessage", UNKNOWN_ACTION_MESSAGE);
            httpRequest.getRequestDispatcher("/controller?command=error").forward(httpRequest, httpResponse);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}

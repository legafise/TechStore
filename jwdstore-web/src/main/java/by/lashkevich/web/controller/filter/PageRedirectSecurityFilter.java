package by.lashkevich.web.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Page redirect security filter.
 * @author Roman Lashkevich
 */
@WebFilter(filterName = "PageRedirectSecurityFilter", initParams = {
        @WebInitParam(name = "ERROR-PAGE-PATH", value = "/controller?command=error")
})
public class PageRedirectSecurityFilter implements Filter {
    private static final String ERROR_PAGE_PATH_INIT_PARAMETER_NAME = "ERROR-PAGE-PATH";
    private String errorPagePath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        errorPagePath = filterConfig.getInitParameter(ERROR_PAGE_PATH_INIT_PARAMETER_NAME);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.sendRedirect(httpRequest.getContextPath() + errorPagePath);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}

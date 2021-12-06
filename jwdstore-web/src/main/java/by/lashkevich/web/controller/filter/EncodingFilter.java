package by.lashkevich.web.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Encoding filter.
 * @author Roman Lashkevich
 */
@WebFilter(filterName = "EncodingFilter", initParams = {
        @WebInitParam(name = "ENCODING", value = "UTF-8", description = "Encoding param")
})
public class EncodingFilter implements Filter {
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) {
        encoding = filterConfig.getInitParameter("ENCODING");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestEncoding = servletRequest.getCharacterEncoding();
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;


        if (encoding != null && !encoding.equals(requestEncoding)) {
            httpServletRequest.setCharacterEncoding(encoding);
            httpServletResponse.setCharacterEncoding(encoding);
        }

        httpServletResponse.setHeader("Cache-Control", "no-cache");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        encoding = null;
    }
}

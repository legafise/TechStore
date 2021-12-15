package by.lashkevich.web.controller.filter;

import by.lashkevich.logic.entity.Role;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.logic.service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * The type Security filter.
 * @author Roman Lashkevich
 */
@WebFilter(filterName = "SecurityFilter")
public class SecurityFilter implements Filter {
    private static final String SESSION_ROLE_PARAMETER = "role";
    private static final String GUEST_ROLE = "guest";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        if (session.getAttribute(SESSION_ROLE_PARAMETER) == null) {
            session.setAttribute(SESSION_ROLE_PARAMETER, GUEST_ROLE);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}

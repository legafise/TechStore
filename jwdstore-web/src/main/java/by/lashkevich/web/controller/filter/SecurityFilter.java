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
    private final UserService userService = (UserService) ServiceFactory.USER_SERVICE.getService();;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        if (session.getAttribute(SESSION_ROLE_PARAMETER) == null) {
            session.setAttribute("role", GUEST_ROLE);
        }

        String currentSessionRole = (String) session.getAttribute(SESSION_ROLE_PARAMETER);
        if (!currentSessionRole.equals(GUEST_ROLE)) {
            Role realUserRole = userService.findUserById(String.valueOf(request.getSession()
                    .getAttribute("userId"))).getRole();
            if (realUserRole != Role.findRoleByName(currentSessionRole)) {
                request.getSession().setAttribute("role", realUserRole.getName());
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}

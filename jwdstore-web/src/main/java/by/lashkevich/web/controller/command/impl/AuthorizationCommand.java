package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.entity.Role;
import by.lashkevich.logic.entity.User;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.logic.service.UserService;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * The type Authorization command.
 *
 * @author Roman Lashkevich
 * @see Command
 */
public class AuthorizationCommand implements Command {
    private final UserService userService;

    /**
     * Instantiates a new Authorization command.
     */
    public AuthorizationCommand() {
        userService = (UserService) ServiceFactory.USER_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) {
        Optional<User> userOptional = userService.findUserByEmail(request.getParameter("email"));

        if (!userOptional.isPresent()) {
            request.getSession().setAttribute("authorizationResult", false);
            return new CommandResult(CommandResult.ResponseType.REDIRECT, "/controller?command=check_authorization");
        }

        User user = userOptional.get();

        if (BCrypt.checkpw(request.getParameter("password"), user.getPassword())) {
            request.getSession().setAttribute("role", user.getRole().getName());
            request.getSession().setAttribute("userId", user.getId());
            request.getSession().setAttribute("balance", user.getBalance());
            if (user.getRole() == Role.BANNED) {
                return new CommandResult(CommandResult.ResponseType.REDIRECT, "/controller?command=ban_page");
            }
            request.getSession().setAttribute("authorizationResult", true);
        } else {
            request.getSession().setAttribute("authorizationResult", false);
        }

        return new CommandResult(CommandResult.ResponseType.REDIRECT, "/controller?command=check_authorization");
    }
}

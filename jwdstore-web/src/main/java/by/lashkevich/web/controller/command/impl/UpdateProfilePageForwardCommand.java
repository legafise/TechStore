package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.logic.service.UserService;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Update profile page forward command.
 *
 * @author Roman Lashkevich
 * @see Command
 */
public class UpdateProfilePageForwardCommand implements Command {
    private final UserService userService;

    /**
     * Instantiates a new Update profile page forward command.
     */
    public UpdateProfilePageForwardCommand() {
        userService = (UserService) ServiceFactory.USER_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) {
        request.setAttribute("user", userService
                .findUserById(String.valueOf(request.getSession().getAttribute("userId"))));
        return new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/update_profile.jsp");
    }
}

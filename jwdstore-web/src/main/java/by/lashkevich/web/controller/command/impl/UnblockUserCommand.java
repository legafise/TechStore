package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.entity.Role;
import by.lashkevich.logic.entity.User;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.logic.service.UserService;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Unblock user command.
 *
 * @author Roman Lashkevich
 * @see Command
 */
public class UnblockUserCommand implements Command {
    private final UserService userService;

    /**
     * Instantiates a new Unblock user command.
     */
    public UnblockUserCommand() {
        userService = (UserService) ServiceFactory.USER_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) {
        User user = userService.findUserById(request.getParameter("userId"));
        user.setRole(Role.USER);
        request.getSession().setAttribute("isUserUnblocked", userService.updateUser(user));
        return new CommandResult(CommandResult.ResponseType.REDIRECT, "/controller?command=user_list");
    }
}

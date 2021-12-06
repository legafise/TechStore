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

public class BanUserCommand implements Command {
    private final UserService userService;

    public BanUserCommand() {
        userService = (UserService) ServiceFactory.USER_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            User user = userService.findUserById(request.getParameter("userId"));
            user.setRole(Role.BANNED);
            request.getSession().setAttribute("isUserBaned", userService.updateUser(user));
            return new CommandResult(CommandResult.ResponseType.REDIRECT, "/controller?command=user_list");
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

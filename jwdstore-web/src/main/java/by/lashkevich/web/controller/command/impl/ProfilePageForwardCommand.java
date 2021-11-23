package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.entity.User;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.logic.service.UserService;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;

public class ProfilePageForwardCommand implements Command {
    private final UserService userService;

    public ProfilePageForwardCommand() {
        userService = (UserService) ServiceFactory.USER_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            User user = userService.findById(String.valueOf(request.getSession().getAttribute("userId")));
            request.setAttribute("user", user);
            request.setAttribute("currentPage", "profile");
            return new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/profile.jsp");
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

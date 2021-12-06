package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.entity.Role;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.logic.service.UserService;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.stream.Collectors;

public class UserListPageForwardCommand implements Command {
    private final UserService userService;

    public UserListPageForwardCommand() {
        userService = (UserService) ServiceFactory.USER_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        request.setAttribute("currentPage", "userList");
        request.setAttribute("userList", userService.findAllUsers().stream()
                .filter(user -> user.getId() != Long.parseLong(String.valueOf(request.getSession()
                        .getAttribute("userId"))))
                .collect(Collectors.toList()));
        request.setAttribute("roles", Arrays.stream(Role.values())
                .filter(role -> role != Role.BANNED)
                .collect(Collectors.toList()));
        return new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/user_list.jsp");
    }
}

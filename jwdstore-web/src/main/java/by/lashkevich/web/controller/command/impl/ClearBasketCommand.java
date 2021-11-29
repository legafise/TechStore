package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.logic.service.UserService;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;

public class ClearBasketCommand implements Command {
    private final UserService userService;

    public ClearBasketCommand() {
        userService = (UserService) ServiceFactory.USER_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        boolean result = userService.removeBasketByUserId(String.valueOf(request
                .getSession().getAttribute("userId")));
        request.getSession().setAttribute("clearingBasketResult", result);
        return new CommandResult(CommandResult.ResponseType.REDIRECT, "/controller?command=basket");
    }
}

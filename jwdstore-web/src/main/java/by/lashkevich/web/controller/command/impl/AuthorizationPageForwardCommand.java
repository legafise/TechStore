package by.lashkevich.web.controller.command.impl;

import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;

public class AuthorizationPageForwardCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        request.setAttribute("currentPage", "sing_in");
        return new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/authorization.jsp");
    }
}

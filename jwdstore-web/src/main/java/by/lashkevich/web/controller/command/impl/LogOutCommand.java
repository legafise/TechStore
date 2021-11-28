package by.lashkevich.web.controller.command.impl;

import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        request.getSession().setAttribute("role", "guest");
        request.getSession().removeAttribute("userId");
        request.getSession().setAttribute("logOutResult", true);
        return new CommandResult(CommandResult.ResponseType.REDIRECT, "/controller?command=catalog");
    }
}

package by.lashkevich.web.controller.command.impl;

import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;

public class ErrorCommand implements Command {
    public CommandResult execute(HttpServletRequest request) {
        return new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/error_page.jsp");
    }
}

package by.lashkevich.web.controller.command.impl;

import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;

public class PaymentPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        request.setAttribute("amount", request.getParameter("amount"));
        return new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/card_page.jsp");
    }
}

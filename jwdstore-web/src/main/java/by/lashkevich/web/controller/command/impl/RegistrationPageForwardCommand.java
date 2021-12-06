package by.lashkevich.web.controller.command.impl;

import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Registration page forward command.
 * @author Roman Lashkevich
 * @see Command
 */
public class RegistrationPageForwardCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        request.setAttribute("currentPage", "registration");
        return new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/registration.jsp");
    }
}

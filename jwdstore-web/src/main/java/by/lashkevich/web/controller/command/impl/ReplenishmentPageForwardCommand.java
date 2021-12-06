package by.lashkevich.web.controller.command.impl;

import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Replenishment page forward command.
 * @author Roman Lashkevich
 * @see Command
 */
public class ReplenishmentPageForwardCommand implements Command {
    private static final String NO_ACCESS_MESSAGE = "No access";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        if (request.getSession().getAttribute("role") == null) {
            throw new CommandException(NO_ACCESS_MESSAGE);
        }
        return new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/replenishment_page.jsp");
    }
}

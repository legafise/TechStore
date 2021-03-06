package by.lashkevich.web.controller.command.impl;

import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Ban page command.
 * @author Roman Lashkevich
 * @see Command
 */
public class BanPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        return new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/ban_page.jsp");
    }
}

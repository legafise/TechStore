package by.lashkevich.web.controller.command.impl;

import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Check good adding result command.
 * @author Roman Lashkevich
 * @see Command
 */
public class CheckGoodAddingResultCommand implements Command {
    private static final String YOU_CANT_DO_IT_NOW_MESSAGE = "You can't do it now";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        CommandResult commandResult = new CommandResult();
        Boolean addingResult = (Boolean) request.getSession().getAttribute("isGoodAdded");

        commandResult.setResponseType(CommandResult.ResponseType.REDIRECT);
        checkGoodAddingResultResultToNull(addingResult, commandResult, request);

        if (commandResult.getPage().isEmpty()) {
            if (addingResult) {
                commandResult.setPage("/controller?command=catalog");
            } else {
                commandResult.setPage("/controller?command=add_good_page");
            }
        }

        return commandResult;
    }

    private void checkGoodAddingResultResultToNull(Boolean addingResult, CommandResult commandResult,
                                                   HttpServletRequest request) {
        if (addingResult == null) {
            request.getSession().setAttribute("errorMessage", YOU_CANT_DO_IT_NOW_MESSAGE);
            commandResult.setPage("/controller?command=error");
        }
    }

}

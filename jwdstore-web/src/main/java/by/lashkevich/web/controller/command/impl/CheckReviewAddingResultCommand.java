package by.lashkevich.web.controller.command.impl;

import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Check review adding result command.
 * @author Roman Lashkevich
 * @see Command
 */
public class CheckReviewAddingResultCommand implements Command {
    private static final String YOU_CANT_DO_IT_NOW_MESSAGE = "You can't do it now";

    @Override
    public CommandResult execute(HttpServletRequest request) {
        CommandResult commandResult = new CommandResult();
        Boolean registrationResult = (Boolean) request.getSession().getAttribute("reviewAddResult");

        commandResult.setResponseType(CommandResult.ResponseType.REDIRECT);
        checkReviewAddResultToNull(registrationResult, commandResult, request);

        if (commandResult.getPage().isEmpty()) {
            commandResult.setPage((String) request.getSession().getAttribute("lastPage"));
            request.getSession().removeAttribute("lastPage");
        }

        return commandResult;
    }

    private void checkReviewAddResultToNull(Boolean result, CommandResult commandResult, HttpServletRequest request) {
        if (result == null) {
            request.getSession().setAttribute("errorMessage", YOU_CANT_DO_IT_NOW_MESSAGE);
            commandResult.setPage("/controller?command=error");
        }
    }
}

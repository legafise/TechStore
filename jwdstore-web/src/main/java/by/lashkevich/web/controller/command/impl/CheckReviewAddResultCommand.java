package by.lashkevich.web.controller.command.impl;

import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;

public class CheckReviewAddResultCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        CommandResult commandResult = new CommandResult();
        Boolean registrationResult = (Boolean) request.getSession().getAttribute("reviewAddResult");

        commandResult.setResponseType(CommandResult.ResponseType.REDIRECT);
        checkReviewAddResultToNull(registrationResult, commandResult);

        if (commandResult.getPage().isEmpty()) {
            commandResult.setPage((String) request.getSession().getAttribute("lastPage"));
            request.getSession().removeAttribute("lastPage");
        }

        return commandResult;
    }

    private void checkReviewAddResultToNull(Boolean result, CommandResult commandResult) {
        if (result == null) {
            commandResult.setPage("/controller?command=error");
        }
    }
}

package by.lashkevich.web.controller.command.impl;

import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;

public class CheckAuthorizationCommand implements Command {
    private static final String YOU_CANT_DO_IT_NOW_MESSAGE = "You can't do it now";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        CommandResult commandResult = new CommandResult();
        Boolean authorizationResult = (Boolean) request.getSession().getAttribute("authorizationResult");

        commandResult.setResponseType(CommandResult.ResponseType.REDIRECT);
        checkAuthorizationResultToNull(authorizationResult, commandResult, request);

        if (commandResult.getPage().isEmpty()) {
            if (authorizationResult) {
                commandResult.setPage("/controller?command=catalog");
            } else {
                commandResult.setPage("/controller?command=authorization_page");
            }
        }

        return commandResult;
    }

    private void checkAuthorizationResultToNull(Boolean result, CommandResult commandResult,
                                                HttpServletRequest request) {
        if (result == null) {
            request.getSession().setAttribute("errorMessage", YOU_CANT_DO_IT_NOW_MESSAGE);
            commandResult.setPage("/controller?command=error");
        }
    }
}

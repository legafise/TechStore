package by.lashkevich.web.controller.command.impl;

import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;

public class CheckRegistrationCommand implements Command {
    private static final String YOU_CANT_DO_IT_NOW_MESSAGE = "You can't do it now";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        CommandResult commandResult = new CommandResult();
        Boolean registrationResult = (Boolean) request.getSession().getAttribute("registrationResult");

        commandResult.setResponseType(CommandResult.ResponseType.REDIRECT);
        checkRegistrationResultToNull(registrationResult, commandResult, request);

        if (commandResult.getPage().isEmpty()) {
            if (registrationResult) {
                commandResult.setPage("/controller?command=authorization_page");
            } else {
                commandResult.setPage("/controller?command=registration_page");
            }
        }

        return commandResult;
    }

    private void checkRegistrationResultToNull(Boolean result, CommandResult commandResult, HttpServletRequest request) {
        if (result == null) {
            request.getSession().setAttribute("errorMessage", YOU_CANT_DO_IT_NOW_MESSAGE);
            commandResult.setPage("/controller?command=error");
        }
    }
}

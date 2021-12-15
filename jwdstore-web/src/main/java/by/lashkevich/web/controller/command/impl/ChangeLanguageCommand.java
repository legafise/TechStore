package by.lashkevich.web.controller.command.impl;

import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;
import by.lashkevich.web.util.PageFinder;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Change language command.
 * @author Roman Lashkevich
 * @see Command
 */
public class ChangeLanguageCommand implements Command {
    private static final String UNKNOWN_LANGUAGE_MESSAGE = "Unknown language";

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String localeName = request.getParameter("locale");
        if (Languages.isExistentLocale(localeName)) {
            request.getSession().setAttribute("locale", localeName);
        } else {
            throw new CommandException(UNKNOWN_LANGUAGE_MESSAGE);
        }

        return new CommandResult(CommandResult.ResponseType.REDIRECT, PageFinder.findLastPage(request));
    }
}

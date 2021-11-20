package by.lashkevich.web.controller.command.impl;

import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class ChangeLanguageCommand implements Command {
    private static final List<String> localeNames;

    static {
        localeNames = Arrays.asList("ru_ru", "en_us");
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String localeName = request.getParameter("locale");
        if (localeNames.contains(localeName.toLowerCase())) {
            request.getSession().setAttribute("locale", localeName);
        }

        String currentPage = request.getHeader("referer").substring(30);
        return new CommandResult(CommandResult.ResponseType.REDIRECT, currentPage);
    }
}

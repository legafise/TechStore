package by.lashkevich.web.controller.command;

import by.lashkevich.web.controller.command.impl.CatalogForwardCommand;
import by.lashkevich.web.controller.command.impl.ChangeLanguageCommand;
import by.lashkevich.web.controller.command.impl.ErrorCommand;
import by.lashkevich.web.controller.command.impl.GoodPageForwardCommand;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public enum CommandFactory {
    CATALOG(new CatalogForwardCommand(), "catalog"),
    ERROR(new ErrorCommand(), "error"),
    CHANGE_LANGUAGE(new ChangeLanguageCommand(), "change_language"),
    GOOD_PAGE_FORWARD(new GoodPageForwardCommand(), "good");

    private static final String UNKNOWN_COMMAND_ERROR_MESSAGE = "Unknown command: %s";
    private static final String COMMAND_PARAMETER_NAME = "command";
    private final Command command;
    private final String commandName;

    CommandFactory(Command command, String commandName) {
        this.command = command;
        this.commandName = commandName;
    }

    public Command getCommand() {
        return command;
    }

    public String getCommandName() {
        return commandName;
    }

    public static Command findCommandByRequest(HttpServletRequest request) throws CommandException {
        String commandNameInUpperCase = request.getParameter(COMMAND_PARAMETER_NAME).toUpperCase();
        return Arrays.stream(CommandFactory.values())
                .filter(currentCommand -> currentCommand.getCommandName().toUpperCase().equals(commandNameInUpperCase))
                .findFirst()
                .map(CommandFactory::getCommand)
                .orElseThrow(() -> new CommandException(String.format(UNKNOWN_COMMAND_ERROR_MESSAGE,
                        commandNameInUpperCase.toLowerCase())));
    }
}

package by.lashkevich.web.controller.command;

import by.lashkevich.web.controller.command.impl.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public enum CommandFactory {
    CATALOG(new CatalogForwardCommand(), "catalog", true),
    ERROR(new ErrorCommand(), "error", true),
    CHANGE_LANGUAGE(new ChangeLanguageCommand(), "change_language", false),
    GOOD_PAGE_FORWARD(new GoodPageForwardCommand(), "good", true),
    AUTHORIZATION_PAGE_FORWARD(new AuthorizationPageForwardCommand(), "authorization_page", true),
    AUTHORIZATION(new AuthorizationCommand(), "authorization", false),
    CHECK_AUTHORIZATION(new CheckAuthorizationCommand(), "check_authorization", true),
    LOG_OUT(new LogOutCommand(), "log_out", true),
    PROFILE_FORWARD(new ProfilePageForwardCommand(), "profile", true),
    REGISTRATION_PAGE(new RegistrationPageForwardCommand(), "registration_page", true);

    private static final String UNKNOWN_COMMAND_ERROR_MESSAGE = "Unknown command: %s";
    private static final String COMMAND_PARAMETER_NAME = "command";
    private final Command command;
    private final String commandName;
    private final boolean isGetMethodCommand;

    CommandFactory(Command command, String commandName, boolean isGetMethodCommand) {
        this.command = command;
        this.commandName = commandName;
        this.isGetMethodCommand = isGetMethodCommand;
    }

    public Command getCommand() {
        return command;
    }

    public String getCommandName() {
        return commandName;
    }

    public boolean isGetMethodCommand() {
        return isGetMethodCommand;
    }

    public static Command findCommandByRequest(HttpServletRequest request,
                                               boolean isGetMethodCommand) throws CommandException {
        String commandNameInUpperCase = request.getParameter(COMMAND_PARAMETER_NAME).toUpperCase();
        return Arrays.stream(CommandFactory.values())
                .filter(currentCommand -> currentCommand.getCommandName().toUpperCase().equals(commandNameInUpperCase)
                        && currentCommand.isGetMethodCommand == isGetMethodCommand)
                .findFirst()
                .map(CommandFactory::getCommand)
                .orElseThrow(() -> new CommandException(String.format(UNKNOWN_COMMAND_ERROR_MESSAGE,
                        commandNameInUpperCase.toLowerCase())));
    }
}

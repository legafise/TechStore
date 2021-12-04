package by.lashkevich.web.controller.filter;

import by.lashkevich.web.controller.command.CommandFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static by.lashkevich.web.controller.command.CommandFactory.*;

public enum AccessLevels {
    COMMON("anyRole", Arrays.asList(CATALOG, PROFILE_FORWARD, ERROR, CHANGE_LANGUAGE, GOOD_PAGE_FORWARD,
            CHECK_AUTHORIZATION, CHECK_REGISTRATION)),
    ADMIN("admin", Arrays.asList(LOG_OUT, PROFILE_FORWARD, REMOVE_REVIEW, REMOVE_GOOD,
            UPDATE_GOOD_PAGE, UPDATE_GOOD, ADD_GOOD_PAGE, ADD_GOOD, CHECK_GOOD_ADDING_RESULT)),
    MODER("moder", Arrays.asList(REMOVE_GOOD, LOG_OUT, PROFILE_FORWARD, REMOVE_REVIEW, REMOVE_GOOD,
            UPDATE_GOOD_PAGE, UPDATE_GOOD, ADD_GOOD_PAGE, ADD_GOOD, CHECK_GOOD_ADDING_RESULT)),
    USER("user", Arrays.asList(ORDERS_PAGE, LOG_OUT, PROFILE_FORWARD, BASKET_PAGE, ORDERS_PAGE, HANDLE_REVIEW,
            CHECK_REVIEW_ADD_RESULT, REMOVE_REVIEW, REGISTRATION_PAGE, PAYMENT, PAYMENT_PAGE_COMMAND, ADD_GOOD_IN_BASKET,
            REMOVE_GOOD_FROM_BASKET, CHANGE_GOOD_QUANTITY, CLEAR_BASKET_COMMAND, PLACE_BASKET_ORDER,
            PLACE_ORDER_BY_BUY_BUTTON, PLACE_ORDER, PLACE_ORDER_PAGE_FORWARD)),
    GUEST("guest", Arrays.asList(REGISTRATION, AUTHORIZATION_PAGE_FORWARD, AUTHORIZATION, REGISTRATION_PAGE,
            ADD_GOOD_IN_BASKET, PLACE_ORDER_BY_BUY_BUTTON, BASKET_PAGE));

    private final String role;
    private final List<CommandFactory> commands;

    AccessLevels(String role, List<CommandFactory> commands) {
        this.role = role;
        this.commands = commands;
    }

    public List<CommandFactory> getCommands() {
        return commands;
    }

    public String getRole() {
        return role;
    }

    public static boolean isAccessibleCommand(HttpServletRequest request) {
        String command = request.getParameter("command");
        if (command == null || command.isEmpty()) {
            return false;
        }

        String role = (String) request.getSession().getAttribute("role");
        String commandInLowerCase = command.toLowerCase();
        List<AccessLevels> levels = Arrays.stream(AccessLevels.values())
                .filter(level -> level.getCommands().stream()
                        .map(CommandFactory::getCommandName)
                        .collect(Collectors.toList())
                        .contains(commandInLowerCase))
                .collect(Collectors.toList());

        return !levels.isEmpty() && (levels.contains(COMMON) || levels.stream()
                .anyMatch(level -> level.getRole().equals(role)));
    }
}

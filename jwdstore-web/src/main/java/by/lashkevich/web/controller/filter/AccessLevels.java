package by.lashkevich.web.controller.filter;

import by.lashkevich.web.controller.command.CommandFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static by.lashkevich.web.controller.command.CommandFactory.*;

/**
 * The enum Access levels.
 * @author Roman Lashkevich
 */
public enum AccessLevels {
    /**
     * Common access levels.
     */
    COMMON("anyRole", Arrays.asList(CATALOG, PROFILE_FORWARD, ERROR, CHANGE_LANGUAGE, GOOD_PAGE_FORWARD,
            CHECK_AUTHORIZATION, CHECK_REGISTRATION)),
    /**
     * Admin access levels.
     */
    ADMIN("admin", Arrays.asList(LOG_OUT, PROFILE_FORWARD, REMOVE_REVIEW, REMOVE_GOOD,
            UPDATE_GOOD_PAGE, UPDATE_GOOD, ADD_GOOD_PAGE, ADD_GOOD, CHECK_GOOD_ADDING_RESULT, MANAGE_ORDERS_COMMAND,
            UPDATE_ORDER_STATUS, UPDATE_PROFILE_PAGE, UPDATE_PROFILE, USER_LIST, UPDATE_USER_ROLE, BAN_USER, UNBLOCK_USER)),
    /**
     * Moder access levels.
     */
    MODER("moder", Arrays.asList(REMOVE_GOOD, LOG_OUT, PROFILE_FORWARD, REMOVE_REVIEW, REMOVE_GOOD,
            UPDATE_GOOD_PAGE, UPDATE_GOOD, ADD_GOOD_PAGE, ADD_GOOD, CHECK_GOOD_ADDING_RESULT, MANAGE_ORDERS_COMMAND,
            UPDATE_ORDER_STATUS, UPDATE_PROFILE_PAGE, UPDATE_PROFILE)),
    /**
     * User access levels.
     */
    USER("user", Arrays.asList(ORDERS_PAGE, LOG_OUT, PROFILE_FORWARD, BASKET_PAGE, ORDERS_PAGE, HANDLE_REVIEW,
            CHECK_REVIEW_ADD_RESULT, REMOVE_REVIEW, REGISTRATION_PAGE, PAYMENT, PAYMENT_PAGE_COMMAND, ADD_GOOD_IN_BASKET,
            REMOVE_GOOD_FROM_BASKET, CHANGE_GOOD_QUANTITY, CLEAR_BASKET_COMMAND, PLACE_BASKET_ORDER,
            PLACE_ORDER_BY_BUY_BUTTON, PLACE_ORDER, PLACE_ORDER_PAGE_FORWARD, REPLENISHMENT_PAGE, UPDATE_PROFILE_PAGE,
            UPDATE_PROFILE)),
    /**
     * Guest access levels.
     */
    GUEST("guest", Arrays.asList(REGISTRATION, AUTHORIZATION_PAGE_FORWARD, AUTHORIZATION, REGISTRATION_PAGE,
            ADD_GOOD_IN_BASKET, PLACE_ORDER_BY_BUY_BUTTON, BASKET_PAGE)),
    /**
     * Banned access levels.
     */
    BANNED("banned", Arrays.asList(BAN_PAGE, LOG_OUT, CHANGE_LANGUAGE));

    private final String role;
    private final List<CommandFactory> commands;

    AccessLevels(String role, List<CommandFactory> commands) {
        this.role = role;
        this.commands = commands;
    }

    /**
     * Gets commands.
     *
     * @return the commands
     */
    public List<CommandFactory> getCommands() {
        return commands;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Is accessible command boolean.
     *
     * @param request the request
     * @return the boolean
     */
    public static boolean isAccessibleCommand(HttpServletRequest request) {
        String command = request.getParameter("command");
        if (command == null || command.isEmpty()) {
            return false;
        }

        String role = (String) request.getSession().getAttribute("role");
        String commandInLowerCase = command.toLowerCase();

        if (role.equals("banned")) {
            return BANNED.getCommands().stream()
                    .map(CommandFactory::getCommandName)
                    .collect(Collectors.toList())
                    .contains(command);
        }

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

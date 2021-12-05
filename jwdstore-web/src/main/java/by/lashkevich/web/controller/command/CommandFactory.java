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
    REGISTRATION_PAGE(new RegistrationPageForwardCommand(), "registration_page", true),
    REGISTRATION(new RegistrationCommand(), "registration", false),
    CHECK_REGISTRATION(new CheckRegistrationCommand(), "check_registration", true),
    BASKET_PAGE(new BasketForwardCommand(), "basket", true),
    ORDERS_PAGE(new OrdersPageForwardCommand(), "orders_page", true),
    HANDLE_REVIEW(new HandleReviewCommand(), "handle_review", false),
    CHECK_REVIEW_ADD_RESULT(new CheckReviewAddingResultCommand(), "check_review", true),
    REMOVE_REVIEW(new RemoveReviewCommand(), "remove_review", false),
    REPLENISHMENT_PAGE(new ReplenishmentPageForwardCommand(), "replenishment_page", true),
    PAYMENT_PAGE_COMMAND(new PaymentPageCommand(), "payment_page", false),
    PAYMENT(new PaymentCommand(), "payment", false),
    ADD_GOOD_IN_BASKET(new AddGoodInBasketCommand(), "add_good_in_basket", false),
    REMOVE_GOOD_FROM_BASKET(new RemoveGoodFromBasketCommand(), "remove_good_from_basket", false),
    CHANGE_GOOD_QUANTITY(new ChangeGoodQuantityCommand(), "change_good_quantity", false),
    CLEAR_BASKET_COMMAND(new ClearBasketCommand(), "clear_basket", false),
    PLACE_BASKET_ORDER(new PlaceOrderFromBasketCommand(), "place_basket_order", false),
    PLACE_ORDER_BY_BUY_BUTTON(new PlaceOrderByBuyButtonCommand(), "place_order_by_buy_button", false),
    PLACE_ORDER(new PlaceOrderCommand(), "place_order", false),
    PLACE_ORDER_PAGE_FORWARD(new PlaceOrderPageForwardCommand(), "place_order_page", false),
    REMOVE_GOOD(new RemoveGoodCommand(), "remove_good", false),
    UPDATE_GOOD_PAGE(new UpdateGoodPageForwardCommand(), "update_good_page", false),
    UPDATE_GOOD(new UpdateGoodCommand(), "update_good", false),
    ADD_GOOD_PAGE(new AddGoodPageForwardCommand(), "add_good_page", true),
    ADD_GOOD(new AddGoodCommand(), "add_good", false),
    CHECK_GOOD_ADDING_RESULT(new CheckGoodAddingResultCommand(),"check_good_adding_result",true),
    MANAGE_ORDERS_COMMAND(new ManageOrdersPageForwardCommand(), "manage_orders", true),
    UPDATE_ORDER_STATUS(new ChangeOrderStatusCommand(), "update_order_status", false),
    UPDATE_PROFILE_PAGE(new UpdateProfilePageForwardCommand(), "update_profile_page", true),
    UPDATE_PROFILE(new UpdateProfileCommand(), "update_profile", false);


    private static final String UNKNOWN_COMMAND_ERROR_MESSAGE = "Unknown command: %s";
    private static final String COMMAND_IS_NULL_MESSAGE = "Command is null";
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
        String commandName = request.getParameter("command");
        if (commandName == null) {
            throw new CommandException(COMMAND_IS_NULL_MESSAGE);
        }

        return Arrays.stream(CommandFactory.values())
                .filter(currentCommand -> currentCommand.getCommandName().toUpperCase()
                        .equals(commandName.toUpperCase())
                        && currentCommand.isGetMethodCommand == isGetMethodCommand)
                .findFirst()
                .map(CommandFactory::getCommand)
                .orElseThrow(() -> new CommandException(String.format(UNKNOWN_COMMAND_ERROR_MESSAGE,
                        commandName.toLowerCase())));
    }
}

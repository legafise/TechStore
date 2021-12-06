package by.lashkevich.web.controller.command;

import by.lashkevich.web.controller.command.impl.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public enum CommandFactory {
    CATALOG(new CatalogForwardCommand(), "catalog", MethodType.GET),
    ERROR(new ErrorCommand(), "error", MethodType.GET),
    CHANGE_LANGUAGE(new ChangeLanguageCommand(), "change_language", MethodType.POST),
    GOOD_PAGE_FORWARD(new GoodPageForwardCommand(), "good", MethodType.GET),
    AUTHORIZATION_PAGE_FORWARD(new AuthorizationPageForwardCommand(), "authorization_page", MethodType.GET),
    AUTHORIZATION(new AuthorizationCommand(), "authorization", MethodType.POST),
    CHECK_AUTHORIZATION(new CheckAuthorizationCommand(), "check_authorization", MethodType.GET),
    LOG_OUT(new LogOutCommand(), "log_out", MethodType.GET),
    PROFILE_FORWARD(new ProfilePageForwardCommand(), "profile", MethodType.GET),
    REGISTRATION_PAGE(new RegistrationPageForwardCommand(), "registration_page", MethodType.GET),
    REGISTRATION(new RegistrationCommand(), "registration", MethodType.POST),
    CHECK_REGISTRATION(new CheckRegistrationCommand(), "check_registration", MethodType.GET),
    BASKET_PAGE(new BasketForwardCommand(), "basket", MethodType.GET),
    ORDERS_PAGE(new OrdersPageForwardCommand(), "orders_page", MethodType.GET),
    HANDLE_REVIEW(new HandleReviewCommand(), "handle_review", MethodType.POST),
    CHECK_REVIEW_ADD_RESULT(new CheckReviewAddingResultCommand(), "check_review", MethodType.GET),
    REMOVE_REVIEW(new RemoveReviewCommand(), "remove_review", MethodType.POST),
    REPLENISHMENT_PAGE(new ReplenishmentPageForwardCommand(), "replenishment_page", MethodType.GET),
    PAYMENT_PAGE_COMMAND(new PaymentPageCommand(), "payment_page", MethodType.POST),
    PAYMENT(new PaymentCommand(), "payment", MethodType.POST),
    ADD_GOOD_IN_BASKET(new AddGoodInBasketCommand(), "add_good_in_basket", MethodType.POST),
    REMOVE_GOOD_FROM_BASKET(new RemoveGoodFromBasketCommand(), "remove_good_from_basket", MethodType.POST),
    CHANGE_GOOD_QUANTITY(new ChangeGoodQuantityCommand(), "change_good_quantity", MethodType.POST),
    CLEAR_BASKET_COMMAND(new ClearBasketCommand(), "clear_basket", MethodType.POST),
    PLACE_BASKET_ORDER(new PlaceOrderFromBasketCommand(), "place_basket_order", MethodType.POST),
    PLACE_ORDER_BY_BUY_BUTTON(new PlaceOrderByBuyButtonCommand(), "place_order_by_buy_button", MethodType.POST),
    PLACE_ORDER(new PlaceOrderCommand(), "place_order", MethodType.POST),
    PLACE_ORDER_PAGE_FORWARD(new PlaceOrderPageForwardCommand(), "place_order_page", MethodType.POST),
    REMOVE_GOOD(new RemoveGoodCommand(), "remove_good", MethodType.POST),
    UPDATE_GOOD_PAGE(new UpdateGoodPageForwardCommand(), "update_good_page", MethodType.POST),
    UPDATE_GOOD(new UpdateGoodCommand(), "update_good", MethodType.POST),
    ADD_GOOD_PAGE(new AddGoodPageForwardCommand(), "add_good_page", MethodType.GET),
    ADD_GOOD(new AddGoodCommand(), "add_good", MethodType.POST),
    CHECK_GOOD_ADDING_RESULT(new CheckGoodAddingResultCommand(),"check_good_adding_result",MethodType.GET),
    MANAGE_ORDERS_COMMAND(new ManageOrdersPageForwardCommand(), "manage_orders", MethodType.GET),
    UPDATE_ORDER_STATUS(new ChangeOrderStatusCommand(), "update_order_status", MethodType.POST),
    UPDATE_PROFILE_PAGE(new UpdateProfilePageForwardCommand(), "update_profile_page", MethodType.GET),
    UPDATE_PROFILE(new UpdateProfileCommand(), "update_profile", MethodType.POST),
    USER_LIST(new UserListPageForwardCommand(), "user_list", MethodType.GET),
    UPDATE_USER_ROLE(new UpdateUserRoleCommand(), "update_user_role", MethodType.POST),
    BAN_USER(new BanUserCommand(), "ban_user", MethodType.POST),
    UNBLOCK_USER(new UnblockUserCommand(), "unblock_user", MethodType.POST),
    BAN_PAGE(new BanPageCommand(), "ban_page", MethodType.GET);
    
    private static final String UNKNOWN_COMMAND_ERROR_MESSAGE = "Unknown command: %s";
    private static final String COMMAND_IS_NULL_MESSAGE = "Command is null";
    private final Command command;
    private final String commandName;
    private final MethodType methodType;

    CommandFactory(Command command, String commandName, MethodType methodType) {
        this.command = command;
        this.commandName = commandName;
        this.methodType = methodType;
    }
    
    public enum MethodType {
        GET,
        POST;
    }

    public Command getCommand() {
        return command;
    }

    public String getCommandName() {
        return commandName;
    }

    public MethodType getMethodType() {
        return methodType;
    }

    public static Command findCommandByRequest(HttpServletRequest request,
                                               MethodType methodType) throws CommandException {
        String commandName = request.getParameter("command");
        if (commandName == null) {
            throw new CommandException(COMMAND_IS_NULL_MESSAGE);
        }

        return Arrays.stream(CommandFactory.values())
                .filter(currentCommand -> currentCommand.getCommandName().toUpperCase()
                        .equals(commandName.toUpperCase())
                        && currentCommand.getMethodType() == methodType)
                .findFirst()
                .map(CommandFactory::getCommand)
                .orElseThrow(() -> new CommandException(String.format(UNKNOWN_COMMAND_ERROR_MESSAGE,
                        commandName.toLowerCase())));
    }
}

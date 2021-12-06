package by.lashkevich.web.controller.command;

import by.lashkevich.web.controller.command.impl.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * The enum Command factory.
 * @author Roman Lashkevich
 */
public enum CommandFactory {
    /**
     * The Catalog.
     */
    CATALOG(new CatalogForwardCommand(), "catalog", MethodType.GET),
    /**
     * The Error.
     */
    ERROR(new ErrorCommand(), "error", MethodType.GET),
    /**
     * The Change language.
     */
    CHANGE_LANGUAGE(new ChangeLanguageCommand(), "change_language", MethodType.POST),
    /**
     * The Good page forward.
     */
    GOOD_PAGE_FORWARD(new GoodPageForwardCommand(), "good", MethodType.GET),
    /**
     * The Authorization page forward.
     */
    AUTHORIZATION_PAGE_FORWARD(new AuthorizationPageForwardCommand(), "authorization_page", MethodType.GET),
    /**
     * The Authorization.
     */
    AUTHORIZATION(new AuthorizationCommand(), "authorization", MethodType.POST),
    /**
     * The Check authorization.
     */
    CHECK_AUTHORIZATION(new CheckAuthorizationCommand(), "check_authorization", MethodType.GET),
    /**
     * The Log out.
     */
    LOG_OUT(new LogOutCommand(), "log_out", MethodType.GET),
    /**
     * The Profile forward.
     */
    PROFILE_FORWARD(new ProfilePageForwardCommand(), "profile", MethodType.GET),
    /**
     * The Registration page.
     */
    REGISTRATION_PAGE(new RegistrationPageForwardCommand(), "registration_page", MethodType.GET),
    /**
     * The Registration.
     */
    REGISTRATION(new RegistrationCommand(), "registration", MethodType.POST),
    /**
     * The Check registration.
     */
    CHECK_REGISTRATION(new CheckRegistrationCommand(), "check_registration", MethodType.GET),
    /**
     * The Basket page.
     */
    BASKET_PAGE(new BasketForwardCommand(), "basket", MethodType.GET),
    /**
     * The Orders page.
     */
    ORDERS_PAGE(new OrdersPageForwardCommand(), "orders_page", MethodType.GET),
    /**
     * The Handle review.
     */
    HANDLE_REVIEW(new HandleReviewCommand(), "handle_review", MethodType.POST),
    /**
     * The Check review add result.
     */
    CHECK_REVIEW_ADD_RESULT(new CheckReviewAddingResultCommand(), "check_review", MethodType.GET),
    /**
     * The Remove review.
     */
    REMOVE_REVIEW(new RemoveReviewCommand(), "remove_review", MethodType.POST),
    /**
     * The Replenishment page.
     */
    REPLENISHMENT_PAGE(new ReplenishmentPageForwardCommand(), "replenishment_page", MethodType.GET),
    /**
     * The Payment page command.
     */
    PAYMENT_PAGE_COMMAND(new PaymentPageCommand(), "payment_page", MethodType.POST),
    /**
     * The Payment.
     */
    PAYMENT(new PaymentCommand(), "payment", MethodType.POST),
    /**
     * The Add good in basket.
     */
    ADD_GOOD_IN_BASKET(new AddGoodInBasketCommand(), "add_good_in_basket", MethodType.POST),
    /**
     * The Remove good from basket.
     */
    REMOVE_GOOD_FROM_BASKET(new RemoveGoodFromBasketCommand(), "remove_good_from_basket", MethodType.POST),
    /**
     * The Change good quantity.
     */
    CHANGE_GOOD_QUANTITY(new ChangeGoodQuantityCommand(), "change_good_quantity", MethodType.POST),
    /**
     * The Clear basket command.
     */
    CLEAR_BASKET_COMMAND(new ClearBasketCommand(), "clear_basket", MethodType.POST),
    /**
     * The Place basket order.
     */
    PLACE_BASKET_ORDER(new PlaceOrderFromBasketCommand(), "place_basket_order", MethodType.POST),
    /**
     * The Place order by buy button.
     */
    PLACE_ORDER_BY_BUY_BUTTON(new PlaceOrderByBuyButtonCommand(), "place_order_by_buy_button", MethodType.POST),
    /**
     * The Place order.
     */
    PLACE_ORDER(new PlaceOrderCommand(), "place_order", MethodType.POST),
    /**
     * The Place order page forward.
     */
    PLACE_ORDER_PAGE_FORWARD(new PlaceOrderPageForwardCommand(), "place_order_page", MethodType.POST),
    /**
     * The Remove good.
     */
    REMOVE_GOOD(new RemoveGoodCommand(), "remove_good", MethodType.POST),
    /**
     * The Update good page.
     */
    UPDATE_GOOD_PAGE(new UpdateGoodPageForwardCommand(), "update_good_page", MethodType.POST),
    /**
     * The Update good.
     */
    UPDATE_GOOD(new UpdateGoodCommand(), "update_good", MethodType.POST),
    /**
     * The Add good page.
     */
    ADD_GOOD_PAGE(new AddGoodPageForwardCommand(), "add_good_page", MethodType.GET),
    /**
     * The Add good.
     */
    ADD_GOOD(new AddGoodCommand(), "add_good", MethodType.POST),
    /**
     * The Check good adding result.
     */
    CHECK_GOOD_ADDING_RESULT(new CheckGoodAddingResultCommand(),"check_good_adding_result",MethodType.GET),
    /**
     * The Manage orders command.
     */
    MANAGE_ORDERS_COMMAND(new ManageOrdersPageForwardCommand(), "manage_orders", MethodType.GET),
    /**
     * The Update order status.
     */
    UPDATE_ORDER_STATUS(new ChangeOrderStatusCommand(), "update_order_status", MethodType.POST),
    /**
     * The Update profile page.
     */
    UPDATE_PROFILE_PAGE(new UpdateProfilePageForwardCommand(), "update_profile_page", MethodType.GET),
    /**
     * The Update profile.
     */
    UPDATE_PROFILE(new UpdateProfileCommand(), "update_profile", MethodType.POST),
    /**
     * The User list.
     */
    USER_LIST(new UserListPageForwardCommand(), "user_list", MethodType.GET),
    /**
     * The Update user role.
     */
    UPDATE_USER_ROLE(new UpdateUserRoleCommand(), "update_user_role", MethodType.POST),
    /**
     * The Ban user.
     */
    BAN_USER(new BanUserCommand(), "ban_user", MethodType.POST),
    /**
     * The Unblock user.
     */
    UNBLOCK_USER(new UnblockUserCommand(), "unblock_user", MethodType.POST),
    /**
     * The Ban page.
     */
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

    /**
     * The enum Method type.
     */
    public enum MethodType {
        /**
         * Get method type.
         */
        GET,
        /**
         * Post method type.
         */
        POST;
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public Command getCommand() {
        return command;
    }

    /**
     * Gets command name.
     *
     * @return the command name
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * Gets method type.
     *
     * @return the method type
     */
    public MethodType getMethodType() {
        return methodType;
    }

    /**
     * Find command by request command.
     *
     * @param request    the request
     * @param methodType the method type
     * @return the command
     * @throws CommandException the command exception
     */
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

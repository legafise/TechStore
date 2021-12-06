package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.entity.Good;
import by.lashkevich.logic.entity.Order;
import by.lashkevich.logic.entity.OrderStatus;
import by.lashkevich.logic.service.OrderService;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.logic.service.UserService;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Map;

/**
 * The type Place order command.
 * @author Roman Lashkevich
 * @see Command
 */
public class PlaceOrderCommand implements Command {
    private static final String BUY_BUTTON = "buyButton";
    private static final String PLACE_ORDER_BUTTON = "placeOrderButton";
    private final UserService userService;
    private final OrderService orderService;

    /**
     * Instantiates a new Place order command.
     */
    public PlaceOrderCommand() {
        userService = (UserService) ServiceFactory.USER_SERVICE.getService();
        orderService = (OrderService) ServiceFactory.ORDER_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            String address = request.getParameter("address");

            if (address == null || address.isEmpty() || address.length() < 7) {
                request.getSession().setAttribute("isInvalidAddress", true);
                return new CommandResult(CommandResult.ResponseType.FORWARD, "/controller?command=place_order_page");
            }

            Order order = new Order();
            Map<Good, Integer> goods = (Map<Good, Integer>) request.getSession().getAttribute("goods");
            request.getSession().removeAttribute("goods");
            order.setStatus(OrderStatus.EXECUTING);
            order.setGoods(goods);
            order.setAddress(address);
            order.setPrice(new BigDecimal(String.valueOf(request.getSession().getAttribute("price"))));
            request.getSession().removeAttribute("price");
            order.setDate(LocalDate.now(Clock.systemDefaultZone()));
            order.setCustomer(userService.findUserById(String.valueOf(request
                    .getSession().getAttribute("userId"))));
            boolean result = orderService.placeOrder(order);
            request.getSession().setAttribute("placingOrderResult", result);
            request.getSession().setAttribute("balance", userService.findUserById(String.valueOf(request
                    .getSession().getAttribute("userId"))).getBalance());

            if (result) {
                String orderButtonName = (String) request.getSession().getAttribute("orderButtonName");
                String userId = String.valueOf(request.getSession().getAttribute("userId"));
                if (orderButtonName != null && orderButtonName.equals(PLACE_ORDER_BUTTON)) {
                    userService.removeBasketByUserId(userId);
                } else if (orderButtonName != null && orderButtonName.equals(BUY_BUTTON)) {
                    userService.removeGoodFromBasket(userId, String.valueOf(goods.keySet().iterator().next().getId()));
                }
            }

            return new CommandResult(CommandResult.ResponseType.REDIRECT, "/controller?command=orders_page");
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
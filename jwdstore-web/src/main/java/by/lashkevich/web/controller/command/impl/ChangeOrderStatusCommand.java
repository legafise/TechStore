package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.entity.Order;
import by.lashkevich.logic.entity.OrderStatus;
import by.lashkevich.logic.entity.OrderStatusException;
import by.lashkevich.logic.service.OrderService;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Change order status command.
 *
 * @author Roman Lashkevich
 * @see Command
 */
public class ChangeOrderStatusCommand implements Command {
    private final OrderService orderService;

    /**
     * Instantiates a new Change order status command.
     */
    public ChangeOrderStatusCommand() {
        orderService = (OrderService) ServiceFactory.ORDER_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) {
        Order order = orderService.findById(request.getParameter("orderId"));
        order.setStatus(OrderStatus.findStatus(request.getParameter("status")));
        request.getSession().setAttribute("isOrderUpdated", orderService.updateOrder(order));
        return new CommandResult(CommandResult.ResponseType.REDIRECT, "/controller?command=manage_orders");
    }
}

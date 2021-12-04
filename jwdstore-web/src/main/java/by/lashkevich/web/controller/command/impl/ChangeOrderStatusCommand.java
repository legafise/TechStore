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

public class ChangeOrderStatusCommand implements Command {
    private static final String INCORRECT_CHANGES_IN_ORDER = "Order was updated incorrect!";
    private final OrderService orderService;

    public ChangeOrderStatusCommand() {
        orderService = (OrderService) ServiceFactory.ORDER_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            Order order = orderService.findById(request.getParameter("orderId"));
            order.setStatus(OrderStatus.findStatus(request.getParameter("status")));
            request.getSession().setAttribute("isOrderUpdated", orderService.updateOrder(order));
            return new CommandResult(CommandResult.ResponseType.REDIRECT, "/controller?command=manage_orders");
        } catch (ServiceException | OrderStatusException e) {
            request.getSession().setAttribute("errorMessage", INCORRECT_CHANGES_IN_ORDER);
            throw new CommandException(e);
        }
    }
}

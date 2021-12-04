package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.entity.OrderStatus;
import by.lashkevich.logic.service.OrderService;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

public class ManageOrdersPageForwardCommand implements Command {
    private final OrderService orderService;

    public ManageOrdersPageForwardCommand() {
        orderService = (OrderService) ServiceFactory.ORDER_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            request.setAttribute("currentPage", "orderList");
            request.setAttribute("orders", orderService.findAllOrders().stream()
                    .filter(order -> order.getStatus() == OrderStatus.IN_PROCESSING
                            || order.getStatus() == OrderStatus.EXECUTING)
                    .sorted((firstOrder, secondOrder) -> firstOrder.getStatus() == OrderStatus.IN_PROCESSING
                            && secondOrder.getStatus() == OrderStatus.EXECUTING ? 1 : -1)
                    .collect(Collectors.toList()));
            request.setAttribute("statuses", OrderStatus.values());
            return new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/manage_orders.jsp");
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.entity.Order;
import by.lashkevich.logic.service.OrderService;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class OrdersPageForwardCommand implements Command {
    private static final String ACTION_IS_BLOCKED = "This action is blocked";
    private final OrderService orderService;

    public OrdersPageForwardCommand() {
        orderService = (OrderService) ServiceFactory.ORDER_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            if (!request.getSession().getAttribute("role").equals("guest")) {
                Optional<List<Order>> orders = orderService.findOrdersByUserId(String.valueOf(request.getSession()
                        .getAttribute("userId")));
                if (orders.isPresent()) {
                    request.setAttribute("orderList", orders.get());
                    request.setAttribute("isEmptyOrderList", false);
                } else {
                    request.setAttribute("isEmptyOrderList", true);
                }

                request.setAttribute("currentPage", "orders");
                return new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/orders.jsp");
            }

            throw new CommandException(ACTION_IS_BLOCKED);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

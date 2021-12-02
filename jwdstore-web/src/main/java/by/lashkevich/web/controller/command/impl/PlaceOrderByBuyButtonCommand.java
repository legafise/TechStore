package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.entity.Good;
import by.lashkevich.logic.service.*;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class PlaceOrderByBuyButtonCommand implements Command {
    private final GoodService goodService;
    private final UserService userService;

    public PlaceOrderByBuyButtonCommand() {
        goodService = (GoodService) ServiceFactory.GOOD_SERVICE.getService();
        userService = (UserService) ServiceFactory.USER_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            if (request.getSession().getAttribute("role").equals("guest")) {
                request.getSession().setAttribute("authorizationInformation", true);
                return new CommandResult(CommandResult.ResponseType.REDIRECT, "/controller?command=authorization_page");
            }

            Map<Good, Integer> goods = new HashMap<>();
            Good good = goodService.findGoodById(request.getParameter("goodId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            goods.put(good, quantity);
            BigDecimal price = good.getPrice().multiply(new BigDecimal(quantity));

            if (userService.findUserById(String.valueOf(request.getSession().getAttribute("userId")))
                    .getBalance().compareTo(price) < 0) {
                request.getSession().setAttribute("isInvalidBalance", true);
                return new CommandResult(CommandResult.ResponseType.REDIRECT,
                        "/controller?command=replenishment_page");
            }

            request.setAttribute("goods", goods);
            request.setAttribute("price", price);

            if (quantity > 1) {
                request.getSession().setAttribute("orderButtonName", "buyButton");
            }

            return new CommandResult(CommandResult.ResponseType.FORWARD, "/controller?command=place_order_page");
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

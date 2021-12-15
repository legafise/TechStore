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

/**
 * The type Place order by buy button command.
 *
 * @author Roman Lashkevich
 * @see Command
 */
public class PlaceOrderByBuyButtonCommand implements Command {
    private final GoodService goodService;
    private final UserService userService;

    /**
     * Instantiates a new Place order by buy button command.
     */
    public PlaceOrderByBuyButtonCommand() {
        goodService = (GoodService) ServiceFactory.GOOD_SERVICE.getService();
        userService = (UserService) ServiceFactory.USER_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) {
        if (request.getSession().getAttribute("role").equals("guest")) {
            request.getSession().setAttribute("authorizationInformation", true);
            return new CommandResult(CommandResult.ResponseType.REDIRECT, "/controller?command=authorization_page");
        }

        Map<Good, Short> goods = new HashMap<>();
        Good good = goodService.findGoodById(request.getParameter("goodId"));
        short quantity = Short.parseShort(request.getParameter("quantity"));
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
    }
}

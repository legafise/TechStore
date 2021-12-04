package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.entity.Basket;
import by.lashkevich.logic.entity.Good;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.logic.service.UserService;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public class PlaceOrderFromBasketCommand implements Command {
    private final UserService userService;

    public PlaceOrderFromBasketCommand() {
        userService = (UserService) ServiceFactory.USER_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            Optional<Basket> basketOptional = userService.findBasketByUserId(String.valueOf(request.getSession()
                    .getAttribute("userId")));
            if (!basketOptional.isPresent()) {
                return new CommandResult(CommandResult.ResponseType.FORWARD, "/controller?command=error");
            }

            Map<Good, Integer> goods = basketOptional.get().getGoods();

            BigDecimal price = new BigDecimal("0.0");
            for (Map.Entry<Good, Integer> entry : goods.entrySet()) {
                BigDecimal priceCopy = new BigDecimal(String.valueOf(price));
                price = priceCopy.add(entry.getKey().getPrice().multiply(new BigDecimal(entry.getValue())));
            }

            if (userService.findUserById(String.valueOf(request.getSession().getAttribute("userId")))
                    .getBalance().compareTo(price) < 0) {
                request.getSession().setAttribute("isInvalidBalance", true);
                return new CommandResult(CommandResult.ResponseType.REDIRECT,
                        "/controller?command=replenishment_page");
            }

            request.setAttribute("goods", goods);
            request.setAttribute("price", price);
            request.getSession().setAttribute("orderButtonName", "placeOrderButton");
            return new CommandResult(CommandResult.ResponseType.FORWARD, "/controller?command=place_order_page");
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
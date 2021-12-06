package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.entity.Basket;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.logic.service.UserService;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * The type Basket forward command.
 * @author Roman Lashkevich
 * @see Command
 */
public class BasketForwardCommand implements Command {
    private final UserService userService;

    /**
     * Instantiates a new Basket forward command.
     */
    public BasketForwardCommand() {
        userService = (UserService) ServiceFactory.USER_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            if (request.getSession().getAttribute("role").equals("guest")) {
                request.getSession().setAttribute("authorizationInformation", true);
                return new CommandResult(CommandResult.ResponseType.FORWARD, "/controller?command=authorization_page");
            }

            Optional<Basket> basketOptional = userService.findBasketByUserId(String.valueOf(request
                    .getSession().getAttribute("userId")));

            if (!basketOptional.isPresent()) {
                request.setAttribute("isBasketEmpty", true);
                return new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/basket.jsp");
            }

            request.setAttribute("isBasketEmpty", false);
            request.setAttribute("basketGoods", basketOptional.get().getGoods().entrySet());
            request.setAttribute("currentPage", "basket");
            return new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/basket.jsp");
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

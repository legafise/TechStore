package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.entity.User;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.logic.service.UserService;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * The type Change good quantity command.
 *
 * @author Roman Lashkevich
 * @see Command
 */
public class ChangeGoodQuantityCommand implements Command {
    private final UserService userService;
    private static final BigDecimal MAX_GOOD_QUANTITY = new BigDecimal("999");
    private static final BigDecimal MIN_GOOD_QUANTITY = new BigDecimal("1");

    /**
     * Instantiates a new Change good quantity command.
     */
    public ChangeGoodQuantityCommand() {
        userService = (UserService) ServiceFactory.USER_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) {
        BigDecimal bigDecimalQuantity = new BigDecimal(request.getParameter("quantity"));
        if (bigDecimalQuantity.compareTo(MAX_GOOD_QUANTITY) < 1 && bigDecimalQuantity.compareTo(MIN_GOOD_QUANTITY) >= 0) {
            User user = userService.findUserById(String.valueOf(request.getSession().getAttribute("userId")));
            Thread.currentThread().setName(user.getId() + user.getLogin());
            boolean updatingResult = userService.changeGoodQuantityInBasket(String.valueOf(user.getId()),
                    request.getParameter("goodId"), request.getParameter("quantity"));
            request.getSession().setAttribute("goodUpdatingResult", updatingResult);
        } else {
            request.getSession().setAttribute("goodUpdatingResult", false);
        }

        return new CommandResult(CommandResult.ResponseType.REDIRECT, "/controller?command=basket");
    }
}

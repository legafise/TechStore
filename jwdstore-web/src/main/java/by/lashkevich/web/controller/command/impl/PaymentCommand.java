package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.entity.User;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.logic.service.UserService;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Payment command.
 *
 * @author Roman Lashkevich
 * @see Command
 */
public class PaymentCommand implements Command {
    private final UserService userService;

    /**
     * Instantiates a new Payment command.
     */
    public PaymentCommand() {
        userService = (UserService) ServiceFactory.USER_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) {
        if (!cardDataValidator(request)) {
            request.setAttribute("amount", request.getParameter("amount"));
            request.setAttribute("isInvalidCardData", true);
            return new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/card_page.jsp");
        }

        User user = userService.findUserById(String.valueOf(request.getSession().getAttribute("userId")));
        Thread.currentThread().setName(user.getId() + user.getLogin());
        boolean paymentResult = userService.upBalance(String.valueOf(request.getParameter("amount")),
                String.valueOf(user.getId()));
        changeBalance(request, paymentResult);
        request.getSession().setAttribute("paymentResult", paymentResult);
        return new CommandResult(CommandResult.ResponseType.REDIRECT, "/controller?command=replenishment_page");
    }

    private void changeBalance(HttpServletRequest request, boolean paymentResult) {
        if (paymentResult) {
            request.getSession().setAttribute("balance", userService.findUserById(String.valueOf(request
                    .getSession().getAttribute("userId"))).getBalance());
        }
    }

    private boolean cardDataValidator(HttpServletRequest request) {
        String number = request.getParameter("number");
        String name = request.getParameter("name");
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String cvv = request.getParameter("cvv");

        return number != null && number.length() >= 13 && number.length() <= 19 && name != null && !name.isEmpty()
                && year != null && !year.isEmpty() && month != null && !month.isEmpty() && cvv != null
                && cvv.length() >= 3;
    }
}

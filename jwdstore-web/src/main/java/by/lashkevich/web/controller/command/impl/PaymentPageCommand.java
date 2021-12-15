package by.lashkevich.web.controller.command.impl;

import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * The type Payment page command.
 * @author Roman Lashkevich
 * @see Command
 */
public class PaymentPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        String amount = request.getParameter("amount");
        if (amount.isEmpty()) {
            request.getSession().setAttribute("paymentResult", false);
            return new CommandResult(CommandResult.ResponseType.REDIRECT, "/controller?command=replenishment_page");
        }

        request.setAttribute("amount", new BigDecimal(amount));
        return new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/card_page.jsp");
    }
}

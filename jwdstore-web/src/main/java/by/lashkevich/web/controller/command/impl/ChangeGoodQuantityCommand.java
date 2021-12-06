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
 * The type Change good quantity command.
 * @author Roman Lashkevich
 * @see Command
 */
public class ChangeGoodQuantityCommand implements Command {
    private final UserService userService;

    /**
     * Instantiates a new Change good quantity command.
     */
    public ChangeGoodQuantityCommand() {
        userService = (UserService) ServiceFactory.USER_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            User user = userService.findUserById(String.valueOf(request.getSession().getAttribute("userId")));
            Thread.currentThread().setName(user.getId() + user.getLogin());
            boolean updatingResult = userService.changeGoodQuantityInBasket(String.valueOf(user.getId()),
                    request.getParameter("goodId"), request.getParameter("quantity"));
            request.getSession().setAttribute("goodUpdatingResult", updatingResult);
            return new CommandResult(CommandResult.ResponseType.REDIRECT, "/controller?command=basket");
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

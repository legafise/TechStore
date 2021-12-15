package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.entity.User;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.logic.service.UserService;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;
import by.lashkevich.web.util.PageFinder;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Add good in basket command.
 *
 * @author Roman Lashkevich
 * @see Command
 */
public class AddGoodInBasketCommand implements Command {
    private final UserService userService;

    /**
     * Instantiates a new Add good in basket command.
     */
    public AddGoodInBasketCommand() {
        userService = (UserService) ServiceFactory.USER_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) {
        if (request.getSession().getAttribute("role").equals("guest")) {
            request.getSession().setAttribute("authorizationInformation", true);
            return new CommandResult(CommandResult.ResponseType.REDIRECT, "/controller?command=authorization_page");
        }

        User user = userService.findUserById(String.valueOf(request.getSession().getAttribute("userId")));
        Thread.currentThread().setName(user.getId() + user.getLogin());
        boolean addingResult = userService.addGoodInBasket(String.valueOf(user.getId()),
                request.getParameter("goodId"));
        request.getSession().setAttribute("basketAddingResult", addingResult);
        return new CommandResult(CommandResult.ResponseType.REDIRECT, PageFinder.findLastPage(request));
    }
}

package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.entity.User;
import by.lashkevich.logic.service.GoodService;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.logic.service.UserService;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;
import by.lashkevich.web.util.PageFinder;

import javax.servlet.http.HttpServletRequest;

public class RemoveGoodCommand implements Command {
    private final GoodService goodService;
    private final UserService userService;

    public RemoveGoodCommand() {
        goodService = (GoodService) ServiceFactory.GOOD_SERVICE.getService();
        userService = (UserService) ServiceFactory.USER_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            User user = userService.findUserById(String.valueOf(request.getSession().getAttribute("userId")));
            Thread.currentThread().setName(user.getId() + user.getLogin());
            boolean isGoodRemoved = goodService.removeGoodById(request.getParameter("goodId"));
            request.getSession().setAttribute("isGoodRemoved", isGoodRemoved);
            return isGoodRemoved ? new CommandResult(CommandResult.ResponseType.REDIRECT,
                    "/controller?command=catalog") : new CommandResult(CommandResult.ResponseType.REDIRECT,
                    PageFinder.findLastPage(request));
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

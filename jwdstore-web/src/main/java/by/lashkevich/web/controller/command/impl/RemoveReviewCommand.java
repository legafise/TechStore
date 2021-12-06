package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.entity.User;
import by.lashkevich.logic.service.ReviewService;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.logic.service.UserService;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;
import by.lashkevich.web.util.PageFinder;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Remove review command.
 * @author Roman Lashkevich
 * @see Command
 */
public class RemoveReviewCommand implements Command {
    private final ReviewService reviewService;
    private final UserService userService;

    /**
     * Instantiates a new Remove review command.
     */
    public RemoveReviewCommand() {
        reviewService = (ReviewService) ServiceFactory.REVIEW_SERVICE.getService();
        userService = (UserService) ServiceFactory.USER_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            User author = userService.findUserById(String.valueOf(request.getSession().getAttribute("userId")));
            Thread.currentThread().setName(author.getId() + author.getLogin());
            request.getSession().setAttribute("isReviewRemoved",
                    reviewService.removeReviewById(request.getParameter("reviewId")));
            return new CommandResult(CommandResult.ResponseType.REDIRECT, PageFinder.findLastPage(request));
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.entity.Review;
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
 * The type Handle review command.
 *
 * @author Roman Lashkevich
 * @see Command
 */
public class HandleReviewCommand implements Command {
    private final ReviewService reviewService;
    private final UserService userService;

    /**
     * Instantiates a new Handle review command.
     */
    public HandleReviewCommand() {
        reviewService = (ReviewService) ServiceFactory.REVIEW_SERVICE.getService();
        userService = (UserService) ServiceFactory.USER_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) {
        request.getSession().setAttribute("lastPage", PageFinder.findLastPage(request));
        if (!validateReview(request)) {
            request.getSession().setAttribute("reviewAddResult", false);
            return new CommandResult(CommandResult.ResponseType.REDIRECT,
                    "/controller?command=check_review");
        }

        User author = userService.findUserById(String.valueOf(request.getSession().getAttribute("userId")));
        Review review = fillReviewData(request, author);
        Thread.currentThread().setName(author.getId() + author.getLogin());
        request.getSession().setAttribute("reviewAddResult", reviewService
                .addReview(review, String.valueOf(request.getParameter("goodId"))));
        return new CommandResult(CommandResult.ResponseType.REDIRECT, "/controller?command=check_review");
    }

    private boolean validateReview(HttpServletRequest request) {
        String content = request.getParameter("reviewContent");
        String rate = request.getParameter("rate");

        return content != null && rate != null && !content.isEmpty() && !rate.isEmpty();
    }

    private Review fillReviewData(HttpServletRequest request, User author) {
        return new Review(Short.parseShort(request.getParameter("rate")),
                request.getParameter("reviewContent"), author);
    }
}

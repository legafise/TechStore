package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.entity.Good;
import by.lashkevich.logic.service.GoodService;
import by.lashkevich.logic.service.ReviewService;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Good page forward command.
 *
 * @author Roman Lashkevich
 * @see Command
 */
public class GoodPageForwardCommand implements Command {
    private static final String GOOD_DOESNT_EXIST_MESSAGE = "This good does not exist";
    private final GoodService goodService;
    private final ReviewService reviewService;

    /**
     * Instantiates a new Good page forward command.
     */
    public GoodPageForwardCommand() {
        goodService = (GoodService) ServiceFactory.GOOD_SERVICE.getService();
        reviewService = (ReviewService) ServiceFactory.REVIEW_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) {
        Good good = goodService.findGoodById(request.getParameter("goodId"));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if (!role.equals("guest")) {
            request.setAttribute("isCratedReview",
                    !reviewService.isCreatedReview(String.valueOf(request.getSession()
                            .getAttribute("userId")), request.getParameter("goodId")));
            request.setAttribute("isBoughtGood", goodService.isBoughtGood(request.getParameter("goodId"),
                    String.valueOf(request.getSession().getAttribute("userId"))));
        }
        request.setAttribute("good", good);
        return new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/good_page.jsp");
    }
}

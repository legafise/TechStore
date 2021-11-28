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

public class GoodPageForwardCommand implements Command {
    private final GoodService goodService;
    private final ReviewService reviewService;

    public GoodPageForwardCommand() {
        goodService = (GoodService) ServiceFactory.GOOD_SERVICE.getService();
        reviewService = (ReviewService) ServiceFactory.REVIEW_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            Good good = goodService.findGoodById(request.getParameter("goodId"));
            Object userId = request.getSession().getAttribute("userId");
            if (userId != null) {
                request.setAttribute("isCratedReview",
                        !reviewService.checkReviewForDuplication(String.valueOf(userId),
                                request.getParameter("goodId")));
            }
            request.setAttribute("good", good);
            return new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/good_page.jsp");
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

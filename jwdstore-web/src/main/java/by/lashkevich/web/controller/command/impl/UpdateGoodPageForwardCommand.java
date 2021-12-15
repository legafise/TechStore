package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.service.GoodService;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Update good page forward command.
 *
 * @author Roman Lashkevich
 * @see Command
 */
public class UpdateGoodPageForwardCommand implements Command {
    private final GoodService goodService;

    /**
     * Instantiates a new Update good page forward command.
     */
    public UpdateGoodPageForwardCommand() {
        goodService = (GoodService) ServiceFactory.GOOD_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) {
        request.setAttribute("good", goodService.findGoodById(request.getParameter("goodId")));
        request.setAttribute("goodTypes", goodService.findAllGoodTypes());
        return new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/update_good.jsp");
    }
}

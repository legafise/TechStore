package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.service.GoodService;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;

public class UpdateGoodPageForwardCommand implements Command {
    private static final String INVALID_GOOD_MESSAGE = "The good is not exist";
    private final GoodService goodService;

    public UpdateGoodPageForwardCommand() {
        goodService = (GoodService) ServiceFactory.GOOD_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            request.setAttribute("good", goodService.findGoodById(request.getParameter("goodId")));
            request.setAttribute("goodTypes", goodService.findAllGoodTypes());
            return new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/update_good.jsp");
        } catch (ServiceException e) {
            throw new CommandException(INVALID_GOOD_MESSAGE, e);
        }
    }
}

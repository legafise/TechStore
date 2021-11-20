package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.entity.Good;
import by.lashkevich.logic.service.GoodService;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;

public class GoodPageForwardCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            GoodService goodService = (GoodService) ServiceFactory.GOOD_SERVICE.getService();
            Good good = goodService.findById(request.getParameter("goodId"));
            request.setAttribute("good", good);
            return new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/good_page.jsp");
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

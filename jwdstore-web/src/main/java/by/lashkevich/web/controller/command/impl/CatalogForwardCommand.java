package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.entity.Good;
import by.lashkevich.logic.service.GoodService;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CatalogForwardCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            GoodService goodService = (GoodService) ServiceFactory.GOOD_SERVICE.getService();
            List<Good> goods = goodService.findAll();
            request.setAttribute("goodList", goods);
            return new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/catalog.jsp");
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
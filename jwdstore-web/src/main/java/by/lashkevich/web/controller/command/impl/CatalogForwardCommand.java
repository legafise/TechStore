package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.entity.Good;
import by.lashkevich.logic.service.GoodService;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The type Catalog forward command.
 *
 * @author Roman Lashkevich
 * @see Command
 */
public class CatalogForwardCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        GoodService goodService = (GoodService) ServiceFactory.GOOD_SERVICE.getService();
        List<Good> goods = goodService.findAllGoods();
        request.setAttribute("goodList", goods);
        request.setAttribute("currentPage", "catalog");
        return new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/catalog.jsp");
    }
}
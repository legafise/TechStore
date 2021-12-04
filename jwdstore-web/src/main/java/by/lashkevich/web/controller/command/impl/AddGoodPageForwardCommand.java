package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.service.GoodService;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.http.HttpServletRequest;

public class AddGoodPageForwardCommand implements Command {
    private final GoodService goodService;

    public AddGoodPageForwardCommand() {
        goodService = (GoodService) ServiceFactory.GOOD_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        request.setAttribute("goodTypes", goodService.findAllGoodTypes());
        return new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/add_good.jsp");
    }

}

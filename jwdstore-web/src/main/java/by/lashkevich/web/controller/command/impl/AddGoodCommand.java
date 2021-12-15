package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.entity.Good;
import by.lashkevich.logic.service.GoodService;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.stream.Collectors;

/**
 * The type Add good command.
 * @author Roman Lashkevich
 * @see Command
 */
public class AddGoodCommand implements Command {
    private static final String INVALID_GOOD_DATA_MESSAGE = "Invalid good data";
    private final GoodService goodService;

    /**
     * Instantiates a new Add good command.
     */
    public AddGoodCommand() {
        goodService = (GoodService) ServiceFactory.GOOD_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) {
        try {
            if (!validateGoodData(request)) {
                request.getSession().setAttribute("isGoodAdded", false);
                return new CommandResult(CommandResult.ResponseType.REDIRECT,
                        "/controller?command=check_good_adding_result");
            }

            Good good = mapGood(request);

            for (Part part : request.getParts()) {
                if (part.getName().equals("good-picture")) {
                    InputStream inputStream = part.getInputStream();
                    InputStreamReader isr = new InputStreamReader(inputStream);
                    new BufferedReader(isr)
                            .lines()
                            .collect(Collectors.joining("\n"));
                    if (!part.getSubmittedFileName().isEmpty()) {
                        String pictureName = good.getName() + "_" + part.getSubmittedFileName();
                        good.setImgName(pictureName);
                        pictureName = "goods_" + good.getName() + "_" + part.getSubmittedFileName();
                        part.write(pictureName);
                    }
                }
            }

            request.getSession().setAttribute("isGoodAdded", goodService.addGood(good));
            return new CommandResult(CommandResult.ResponseType.REDIRECT,
                    "/controller?command=check_good_adding_result");
        } catch (IOException | ServletException e) {
            throw new CommandException(INVALID_GOOD_DATA_MESSAGE, e);
        }
    }

    private Good mapGood(HttpServletRequest request) {
        Good good = new Good();
        good.setType(goodService.findTypeById(request.getParameter("typeId")));
        good.setDescription(request.getParameter("description"));
        good.setPrice(new BigDecimal(request.getParameter("price")));
        good.setName(request.getParameter("name"));
        return good;
    }

    private boolean validateGoodData(HttpServletRequest request) {
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String typeId = request.getParameter("typeId");
        String description = request.getParameter("description");
        return name != null && price != null && typeId != null && description != null && !name.isEmpty()
                && !price.isEmpty() && !typeId.isEmpty() && !description.isEmpty();
    }
}

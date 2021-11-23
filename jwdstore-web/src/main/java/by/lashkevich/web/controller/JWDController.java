package by.lashkevich.web.controller;

import by.lashkevich.logic.dao.pool.ConnectionPool;
import by.lashkevich.logic.dao.pool.ConnectionPoolException;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandFactory;
import by.lashkevich.web.controller.command.CommandResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
@MultipartConfig(location = "C:\\IdeaProjects\\JWDStore\\jwdstore-web\\src\\main\\web\\download")
public class JWDController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getRootLogger();

    @Override
    public void init() {
        try {
            LOGGER.debug("init");
            ConnectionPool.getInstance().initializeConnectionPool(3);
        } catch (ConnectionPoolException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void destroy() {
        try {
            ConnectionPool.getInstance().closeConnections();
        } catch (ConnectionPoolException e) {
            LOGGER.error(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            handleRequest(req, resp, CommandFactory.findCommandByRequest(req, true));
        } catch (CommandException e) {
            LOGGER.error(e);
            handleRequest(req, resp, CommandFactory.ERROR.getCommand());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            handleRequest(req, resp, CommandFactory.findCommandByRequest(req, false));
        } catch (CommandException e) {
            LOGGER.error(e);
            handleRequest(req, resp, CommandFactory.ERROR.getCommand());
        }
    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp, Command command) throws IOException {
        try {
            CommandResult commandResult = command.execute(req);

            if (commandResult.getResponseType() == CommandResult.ResponseType.FORWARD) {
                req.getRequestDispatcher(commandResult.getPage()).forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + commandResult.getPage());
            }
        } catch (ServletException | IOException | CommandException e) {
            LOGGER.error(e);
            resp.sendRedirect(req.getContextPath() + "/controller?command=error");
        }
    }
}

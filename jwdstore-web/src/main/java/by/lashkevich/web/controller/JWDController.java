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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
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
            Command command = CommandFactory.findCommandByRequest(req);
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}

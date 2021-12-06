package by.lashkevich.web.controller.command;

import javax.servlet.http.HttpServletRequest;

/**
 * The interface for implementation command pattern.
 * @author Roman Lashkevich
 */
public interface Command {
    /**
     * Execute command result.
     *
     * @param request the request
     * @return the command result
     * @throws CommandException the command exception
     */
    CommandResult execute(HttpServletRequest request) throws CommandException;
}

package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.entity.User;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.logic.service.UserService;
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
import java.time.LocalDate;
import java.util.stream.Collectors;

public class UpdateProfileCommand implements Command {
    private static final String INVALID_USER_DATA = "Invalid user data";
    private final UserService userService;

    public UpdateProfileCommand() {
        userService = (UserService) ServiceFactory.USER_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            if (!validateUserData(request)) {
                request.getSession().setAttribute("isUserUpdated", false);
                return new CommandResult(CommandResult.ResponseType.REDIRECT,
                        "/controller?command=profile");
            }

            User user = userService.findUserById(String.valueOf(request.getSession().getAttribute("userId")));
            updateUserData((user), request);

            for (Part part : request.getParts()) {
                if (part.getName().equals("profilePhoto")) {
                    InputStream inputStream = part.getInputStream();
                    InputStreamReader isr = new InputStreamReader(inputStream);
                    new BufferedReader(isr)
                            .lines()
                            .collect(Collectors.joining("\n"));
                    if (!part.getSubmittedFileName().isEmpty()) {
                        String pictureName = user.getName() + "_" + part.getSubmittedFileName();
                        user.setProfilePictureName(pictureName);
                        pictureName = "users_" + user.getName() + "_" + part.getSubmittedFileName();
                        part.write(pictureName);
                    }
                }
            }

            request.getSession().setAttribute("isUserUpdated", userService.updateUser(user));
            return new CommandResult(CommandResult.ResponseType.REDIRECT,
                    "/controller?command=profile");
        } catch (ServiceException | IOException | ServletException e) {
            throw new CommandException(INVALID_USER_DATA, e);
        }
    }

    private boolean validateUserData(HttpServletRequest request) {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String birthDate = request.getParameter("birthDate");
        String email = request.getParameter("email");

        return name != null && surname != null && email != null && birthDate != null && !name.isEmpty()
                && !surname.isEmpty() && !email.isEmpty() && !birthDate.isEmpty();
    }

    private void updateUserData(User user, HttpServletRequest request) {
        user.setEmail(request.getParameter("email"));
        user.setSurname(request.getParameter("surname"));
        user.setName(request.getParameter("name"));
        user.setBirthDate(LocalDate.parse(request.getParameter("birthDate")));
    }
}

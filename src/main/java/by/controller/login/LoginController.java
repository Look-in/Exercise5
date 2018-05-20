package by.controller.login;

import by.entity.User;
import by.service.UserService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Сервлет для авторизации пользователя. При успешном
 * логине переадресует на список забегов.
 *
 * @author Serg Shankunas
 */
@WebServlet(
        name = "loginForm",
        description = "Сервлет для авторизации",
        urlPatterns = "/loginForm")
public class LoginController extends javax.servlet.http.HttpServlet {

    @Inject
    private UserService userService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null || username.isEmpty()) {
            messages.put("Имя пользователя", "Введите логин");
        }
        if (password == null || password.isEmpty()) {
            messages.put("Пароль", "Введите пароль");
        }
        if (messages.isEmpty()) {
            User user = userService.checkPasswordAndGetUser(username, password);
            if (user != null) {
                request.getSession().setAttribute("user", user);
                if (user.getRole().getRole().equals("client") && user.getRates().size() > 0) {
                    request.getSession().setAttribute("countUserRates", user.getRates().size());
                }
                response.sendRedirect(request.getContextPath() + "/view-race");
                return;
            }
            messages.put("Логин", "Неверные имя пользователя/пароль");
        }
        request.setAttribute("messages", messages);
        request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
    }

}

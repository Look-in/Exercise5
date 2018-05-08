package by.controller;

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

@WebServlet(
        name = "loginForm",
        description = "Сервлет для авторизации",
        urlPatterns = "/loginForm")
public class LoginController extends javax.servlet.http.HttpServlet {

    @Inject
    private UserService userService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Map<String, String> messages = new HashMap<>();
        if (username == null || username.isEmpty()) {
            messages.put("username", "Please enter username");
        }
        if (password == null || password.isEmpty()) {
            messages.put("password", "Please enter password");
        }
        if (messages.isEmpty()) {
            User user = userService.checkPasswordAndGetUser(username,password);
            if (user != null) {
                request.getSession().setAttribute("user", user);
                //request.getRequestDispatcher("selectitemservlet").forward(request, response);
                response.sendRedirect(request.getContextPath() + "/view-race");
                return;
            } else {
                messages.put("login", "Unknown login, please try again");
            }
        }
        request.setAttribute("messages", messages);
        request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
        }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
    }

}

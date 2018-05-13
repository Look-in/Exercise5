package by.controller.login;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(
        name = "logoutForm",
        description = "Сервлет для авторизации",
        urlPatterns = "/logout")
public class LogoutController extends javax.servlet.http.HttpServlet {

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session=request.getSession();
        session.invalidate();
        response.sendRedirect("/view-race");
    }

     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logout(request, response);
     }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logout(request, response);
    }
}

package by.servlet;

import by.dao.RaceDao;
import by.entity.User;
import by.entity.event.AttributeToCompare;
import by.service.UserService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "SelectItemServlet",
        description = "Сервлет для передачи списка товаров",
        urlPatterns = "/selectitemservlet")
public class SelectItemServlet extends javax.servlet.http.HttpServlet {

    @Inject
    RaceDao raceDao;

    @Inject
    UserService userService;

    private void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("sortBy", AttributeToCompare.values());
        /*if (request.getParameter("sortingBy") != null) {
            ItemComparator.compare(item, AttributeToCompare.valueOf(request.getParameter("sortingBy")));
        }*/
        User user = userService.checkPasswordAndGetUser("user","user");
        System.out.println(user.foo());
        System.out.println(user.getRates());
        System.out.println(user.foo());
        request.setAttribute("race", raceDao.getRaces());
        request.getRequestDispatcher("jsp/item_list.jsp").forward(request, response);
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doRequest(request, response);
    }

}

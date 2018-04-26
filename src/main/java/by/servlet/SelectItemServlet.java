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
        Long timeJsp = System.currentTimeMillis();
        User user = null;
        User user1 = null;
        User user2 = null;
        for (int i = 0; i < 1000; i++) {
            user = userService.checkPasswordAndGetUser("user","user");
            user1 = userService.checkPasswordAndGetUser("user1","user1");
            user2 = userService.checkPasswordAndGetUser("user2","user2");
            user.getRates();
            user1.getRates();
            user2.getRates();
        }
        float delta = (float)(System.currentTimeMillis() - timeJsp)/1000;
        System.out.println(delta);
        System.out.println(user.toString());
        System.out.println(user1.toString());
        System.out.println(user2.toString());
        System.out.println(user.getRates());
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

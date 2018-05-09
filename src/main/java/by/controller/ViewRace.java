package by.controller;

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
        name = "ViewRace",
        description = "Сервлет для передачи списка забегов",
        urlPatterns = "/view-race")
public class ViewRace extends javax.servlet.http.HttpServlet {

    @Inject
    RaceDao raceDao;

    private void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("sortBy", AttributeToCompare.values());
        /*if (request.getParameter("sortingBy") != null) {
            ItemComparator.compare(item, AttributeToCompare.valueOf(request.getParameter("sortingBy")));
        }*/
        request.setAttribute("race", raceDao.getRaces());
        request.getRequestDispatcher("jsp/view-race.jsp").forward(request, response);
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doRequest(request, response);
    }

}

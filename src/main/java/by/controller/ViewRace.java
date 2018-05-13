package by.controller;

import by.dao.RaceDao;
import org.jboss.weld.bean.SessionBean;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(
        name = "ViewRace",
        description = "Сервлет для передачи списка забегов",
        urlPatterns = "/view-race")
public class ViewRace extends javax.servlet.http.HttpServlet {

    @Inject
    private RaceDao raceDao;

    private void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("race", raceDao.getRaces());
        request.setAttribute("type", "Скачки");
        request.getRequestDispatcher("jsp/view-race.jsp").forward(request, response);
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doRequest(request, response);
    }

}

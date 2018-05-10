package by.controller;

import by.Utils.HttpServletRequestReflectionUtils;
import by.entity.Race;
import by.service.RaceService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "modify race servlet",
        description = "Сервлет для отображения страницы модификации race",
        urlPatterns = "/modify-race")

public class ModifyRace extends HttpServlet {

    @Inject
    private RaceService raceService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("delete".equalsIgnoreCase(request.getParameter("action"))) {
            doDelete(request, response);
            return;
        }
        Race race = HttpServletRequestReflectionUtils.getEntityFromHttpRequest(Race.class, request, null);
        raceService.pushRace(race);
        response.sendRedirect(request.getContextPath() + "/view-race");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Race race = (request.getParameter("id") != null) ?
                raceService.getRace(Integer.valueOf(request.getParameter("id"))) :
                new Race();
        request.setAttribute("elem", race);
        request.getRequestDispatcher("jsp/modify-race.jsp").forward(request, response);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") != null) {
            raceService.deleteRace(Integer.valueOf(request.getParameter("id")));
        }
        response.sendRedirect(request.getContextPath() + "/view-race");
    }

}

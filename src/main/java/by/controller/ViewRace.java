package by.controller;

import by.Utils.RaceSortUtil;
import by.dao.RaceDao;
import by.entity.Race;
import by.service.reference.AttributeToCompare;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "ViewRace",
        description = "Сервлет для передачи списка забегов",
        urlPatterns = "/view-race")
public class ViewRace extends HttpServlet {

    @Inject
    private RaceDao raceDao;

    private void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("sortBy", AttributeToCompare.values());
        List<Race> races = raceDao.getRaces();
        String sortingBy = request.getParameter("sortingBy");
        if (sortingBy != null && !sortingBy.equals("")) {
            RaceSortUtil.compare(races, AttributeToCompare.valueOf(request.getParameter("sortingBy")));
        }
        request.setAttribute("race", races);
        request.getRequestDispatcher("WEB-INF/jsp/view-race.jsp").forward(request, response);
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doRequest(request, response);
    }

}

package by.controller;

import by.Utils.HttpServletRequestReflectionUtils;
import by.entity.Race;
import by.entity.Rate;
import by.service.RaceService;
import by.service.RateService;
import by.service.reference.ReferenceService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet(
        name = "modify race servlet",
        description = "Сервлет для отображения страницы модификации race",
        urlPatterns = "/modify-race")

public class ModifyRace extends HttpServlet {

    @Inject
    private RaceService raceService;

    @Inject
    private RateService rateService;

    @Inject
    private ReferenceService referenceService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if ("delete".equalsIgnoreCase(request.getParameter("action"))) {
            doDelete(request, response);
            return;
        }
        Race race = HttpServletRequestReflectionUtils.getEntityFromHttpRequest(Race.class, request, null);
        String message;
        if (race.getId() == null || rateService.isAllNewRates(race.getId())) {
            raceService.pushRace(race);
            message = String.format("Забег %s сохранён", race.getId());
        } else {
            message = String.format("Невозможно сохранить забег %s. В забеге есть сыгранные ставки", race.getId());
        }
        request.getSession().setAttribute("message", message);
        response.sendRedirect(request.getContextPath() + "/view-race");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Race race = (request.getParameter("id") != null) ?
                raceService.getRace(Integer.valueOf(request.getParameter("id"))) :
                new Race();
        List<Rate> rates = (race.getId() != null) ? rateService.getRatesForRace(race.getId()) : Collections.EMPTY_LIST;
        request.setAttribute("race", race);
        request.setAttribute("rates", rates);
        request.setAttribute("rateResults", referenceService.getRateResults());
        request.getRequestDispatcher("WEB-INF/jsp/modify-race.jsp").forward(request, response);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String message = null;
        if (request.getParameter("id") != null) {
            int id = Integer.valueOf(request.getParameter("id"));
            if (!rateService.hasRates(id)) {
                raceService.deleteRace(id);
                message = String.format("Забег %s успешно удален", id);
            } else {
                message = String.format("Невозможно удалить забег %s со ставками", id);
            }
        }
        request.getSession().setAttribute("message", message);
        response.sendRedirect(request.getContextPath() + "/view-race");
    }

}

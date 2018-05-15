package by.controller;

import by.entity.Race;
import by.entity.Rate;
import by.entity.User;
import by.service.RaceService;
import by.service.RateService;
import by.service.UserRateService;

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
        name = "Place rate servlet",
        description = "Сервлет для добавления ставки в польз корзину",
        urlPatterns = "/place-rate")

public class PlaceRate extends HttpServlet {

    @Inject
    private RaceService raceService;

    @Inject
    private RateService rateService;

    @Inject
    private UserRateService userRateService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if ("delete".equalsIgnoreCase(request.getParameter("action"))) {
            doDelete(request, response);
            return;
        }
        User user = (User) request.getSession().getAttribute("user");
        Rate rate = (request.getParameter("id") != null) ? rateService.getRate(Integer.valueOf(request.getParameter("id"))) : null;
        if (rate != null) {
            String message;
            if (rate.getRateResult().getId() == 1 && user.getRates().stream().noneMatch(e -> e.getId().equals(rate.getId()))) {
                userRateService.placeUserRate(user, rate);
                message = String.format("Ставка %s сделана", rate.getId());
            } else {
                message = "Повторное добавление ставки или ставка уже в игре!!!";
            }
            request.getSession().setAttribute("message", message);
            response.sendRedirect("/place-rate?id=" + rate.getRace().getId());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Race race = (request.getParameter("id") != null) ?
                raceService.getRace(Integer.valueOf(request.getParameter("id"))) :
                new Race();
        List<Rate> rates = (race.getId() != null) ? rateService.getRatesForRace(race.getId()) : Collections.EMPTY_LIST;
        request.setAttribute("race", race);
        request.setAttribute("rates", rates);
        request.getRequestDispatcher("WEB-INF/jsp/modify-race.jsp").forward(request, response);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String message = null;
        if (request.getParameter("id") != null) {
            int id = Integer.valueOf(request.getParameter("id"));
            if (rateService.isAllNewRates(id)) {
                raceService.deleteRace(id);
                message = String.format("Забег %s успешно удален", id);
            } else {
                message = String.format("Невозможно удалить забег %s с сыгранными ставками", id);
            }
        }
        request.getSession().setAttribute("message", message);
        response.sendRedirect("/view-race");
    }

}

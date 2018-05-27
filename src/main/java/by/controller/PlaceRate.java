package by.controller;

import by.entity.Race;
import by.entity.Rate;
import by.entity.User;
import by.service.RaceService;
import by.service.RateService;
import by.service.UserRateService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Сервлет для добавления ставки в польз корзину.
 *
 * @author Serg Shankunas
 */
@WebServlet(
        name = "Place rate servlet",
        description = "Сервлет для добавления ставки в польз корзину",
        urlPatterns = "/place-rate")
public class PlaceRate extends HttpServlet {

    private RaceService raceService;

    private RateService rateService;

    private UserRateService userRateService;

    @Autowired
    public PlaceRate(RaceService raceService, RateService rateService, UserRateService userRateService) {
        this.raceService = raceService;
        this.rateService = rateService;
        this.userRateService = userRateService;
    }

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
                request.getSession().setAttribute("countUserRates", user.getRates().size());
                message = String.format("Ставка %s сделана", rate.getId());
            } else {
                message = "Повторное добавление ставки или ставка уже в игре!!!";
            }
            request.getSession().setAttribute("message", message);
            response.sendRedirect(request.getContextPath() + "/place-rate?id=" + rate.getRace().getId());
        }
    }

    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Race race = (request.getParameter("id") != null) ?
                raceService.getRace(Integer.valueOf(request.getParameter("id"))) : new Race();
        if (race.getId() != null) {
            List<Rate> rates = (race.getId() != null) ? rateService.getRatesForRace(race.getId()) : Collections.EMPTY_LIST;
            request.setAttribute("rates", rates);
        } else {
            User user = (User) request.getSession().getAttribute("user");
            Set<Rate> rates = user.getRates();
            request.setAttribute("rates", rates);
            race.setRace("Ставки пользователя");
        }
        request.setAttribute("race", race);
        request.getRequestDispatcher("WEB-INF/jsp/modify-race.jsp").forward(request, response);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String message;
        Integer id = request.getParameter("id") != null ? Integer.valueOf(request.getParameter("id")) : null;
        Rate rate = (id != null) ? rateService.getRate(id) : null;
        if (rate != null && rate.getRateResult().getId() == 1) {
            User user = (User) request.getSession().getAttribute("user");
            userRateService.replaceUserRate(user, rate);
            message = String.format("Ставка %s успешно удалена", id);
        } else {
            message = String.format("Невозможно удалить сыгранную ставку %s", id);
        }
        request.getSession().setAttribute("message", message);
        response.sendRedirect(request.getContextPath() + "/view-race");
    }
}

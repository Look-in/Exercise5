package by.controller;

import by.entity.Rate;
import by.service.RateService;
import by.service.reference.ReferenceService;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "modify rate.rateResult servlet",
        description = "Сервлет для модификации результата ставки",
        urlPatterns = "/change-rateResult")

public class ChangeRateResult extends HttpServlet {

    @Inject
    private RateService rateService;

    @Inject
    private ReferenceService referenceService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer raceId = request.getParameter("raceId") != null ? Integer.valueOf(request.getParameter("raceId")) : null;
        Integer raceResult = request.getParameter("rateResult") != null ? Integer.valueOf(request.getParameter("rateResult")) : null;
        Rate rate = (request.getParameter("rateId") != null) ?
                rateService.getRate(Integer.valueOf(request.getParameter("rateId"))) : null;
        String message = null;
        if (raceResult != null && rate != null) {
            rate.setRateResult(referenceService.getRateResult(raceResult));
            rateService.pushRate(rate);
            message = String.format("Результат ставки '%s' изменен на '%s'", rate.getRate(),rate.getRateResult().getRateResult());
        }
        request.getSession().setAttribute("message", message);
        response.sendRedirect("/modify-race?id=" + raceId);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    }

}

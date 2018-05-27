package by.controller;

import by.entity.Rate;
import by.service.RateService;
import by.service.reference.ReferenceService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет для модификации результата ставки
 *
 * @author Serg Shankunas
 */
@WebServlet(
        name = "modify rate.rateResult servlet",
        description = "Сервлет для модификации результата ставки",
        urlPatterns = "/change-rateResult")

public class ChangeRateResult extends HttpServlet {

    private RateService rateService;

    private ReferenceService referenceService;

    @Autowired
    public ChangeRateResult(RateService rateService, ReferenceService referenceService) {
        this.rateService = rateService;
        this.referenceService = referenceService;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer raceId = request.getParameter("raceId") != null ? Integer.valueOf(request.getParameter("raceId")) : null;
        Integer raceResult = request.getParameter("rateResult") != null ? Integer.valueOf(request.getParameter("rateResult")) : null;
        Rate rate = (request.getParameter("rateId") != null) ?
                rateService.getRate(Integer.valueOf(request.getParameter("rateId"))) : null;
        String message = null;
        if (raceResult != null && rate != null) {
            rate.setRateResult(referenceService.getRateResult(raceResult));
            rateService.pushRate(rate);
            message = String.format("Результат ставки '%s' изменен на '%s'", rate.getRate(), rate.getRateResult().getRateResult());
        }
        request.getSession().setAttribute("message", message);
        response.sendRedirect(request.getContextPath() + "/modify-race?id=" + raceId);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    }

}

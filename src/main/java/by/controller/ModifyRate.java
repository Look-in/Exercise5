package by.controller;

import by.Utils.HttpServletRequestReflectionUtils;
import by.entity.Rate;
import by.service.RateService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "modify rate servlet",
        description = "Сервлет для отображения страницы модификации rate",
        urlPatterns = "/modify-rate")

public class ModifyRate extends HttpServlet {

    @Inject
    private RateService rateService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if ("delete".equalsIgnoreCase(request.getParameter("action"))) {
            doDelete(request, response);
            return;
        }
        String message;
        Rate rate = HttpServletRequestReflectionUtils.getEntityFromHttpRequest(Rate.class, request, null);
        if (rateService.isAllNewRates(rate.getRace().getId())) {
            rateService.pushRate(rate);
            message = String.format("Ставка %s сохранена", rate.getId());
        } else {
            message = String.format("Невозможно сохранить ставку %s. В забеге есть сыгранные ставки", rate.getId());
        }
        request.getSession().setAttribute("message",message);
        response.sendRedirect("/modify-race?id=" + rate.getRace().getId());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int raceId = (request.getParameter("raceId") != null) ? Integer.valueOf(request.getParameter("raceId")) : 0;
        Rate rate = (request.getParameter("id") != null) ?
                rateService.getRate(Integer.valueOf(request.getParameter("id"))) :
                rateService.getNewRate(raceId);
        request.setAttribute("rate", rate);
        request.getRequestDispatcher("WEB-INF/jsp/modify-rate.jsp").forward(request, response);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String message = null;
        if (request.getParameter("id") != null ) {
            int id = Integer.valueOf(request.getParameter("id"));
            if (rateService.getRateResultOfRate(id) == 1) {
                rateService.deleteRate(id);
                message = String.format("Ставка %s успешно удалена", id);
            } else {
                message = String.format("Невозможно удалить ставку %s. Статус не 'Новая ставка'", id);
            }
        }
        request.getSession().setAttribute("message",message);
        response.sendRedirect(request.getContextPath() + "/view-race");
    }

}

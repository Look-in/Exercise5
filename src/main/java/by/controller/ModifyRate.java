package by.controller;

import by.entity.Rate;
import by.service.RateService;
import by.service.reference.ReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServlet;

/**
 * Контроллер для отображения страницы модификации rate.
 *
 * @author Serg Shankunas
 */
@Controller
@RequestMapping("/modify-rate")
public class ModifyRate extends HttpServlet {

    private RateService rateService;

    private ReferenceService referenceService;

    @Autowired
    public ModifyRate(RateService rateService, ReferenceService referenceService) {
        this.rateService = rateService;
        this.referenceService = referenceService;
    }

    @ModelAttribute(value = "race")
    public Rate newRequest(@RequestParam (required = false) Integer raceId, @RequestParam(required = false) Integer id) {
        return raceId == null ? null : id != null ? rateService.getRate(id) : rateService.getNewRate(raceId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doPost(Rate rate, RedirectAttributes redirectAttributes, SessionStatus sessionStatus) {
      /*  if ("delete".equalsIgnoreCase(request.getParameter("action"))) {
            doDelete(request, response);
            return;
        }*/
        String message;
        if (rate.getRace() != null && rateService.isAllNewRates(rate.getRace().getId())) {
            rateService.pushRate(rate);
            message = String.format("Ставка %s сохранена", rate.getId());
        } else {
            message = String.format("Невозможно сохранить ставку %s. В забеге есть сыгранные ставки", rate.getId());
        }
        redirectAttributes.addAttribute("message", message);
        sessionStatus.setComplete();
        return "redirect:/modify-race?id=" + rate.getRace().getId();
    }

    @RequestMapping(method = RequestMethod.GET)
    public void doGet() {
    }

    /*protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String message = null;
        if (request.getParameter("id") != null) {
            int id = Integer.valueOf(request.getParameter("id"));
            if (rateService.getRateResultOfRate(id) == 1) {
                rateService.deleteRate(id);
                message = String.format("Ставка %s успешно удалена", id);
            } else {
                message = String.format("Невозможно удалить ставку %s. Статус не 'Новая ставка'", id);
            }
        }
        request.getSession().setAttribute("message", message);
        if (request.getParameter("raceId") != null) {
            response.sendRedirect(request.getContextPath() + "/modify-race?id=" + request.getParameter("raceId"));
        } else {
            response.sendRedirect(request.getContextPath() + "/view-race");
        }
    }*/

    @RequestMapping(value = "/change-rateResult", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public @ResponseBody String changeRateResult(Integer rateId, Integer rateResult) {
        rateService.changeRateResult(rateId, rateResult);
        return String.format("Результат ставки '%s' изменен на '%s'", rateId, rateResult);
    }
}

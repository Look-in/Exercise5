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
public class ModifyRate extends HttpServlet {

    private RateService rateService;

    private ReferenceService referenceService;

    @Autowired
    public ModifyRate(RateService rateService, ReferenceService referenceService) {
        this.rateService = rateService;
        this.referenceService = referenceService;
    }

    @ModelAttribute(value = "race")
    public Rate newRequest(@RequestParam Integer raceId, @RequestParam(required = false) Integer id) {
        return  id != null ? rateService.getRate(id) : rateService.getNewRate(raceId);
    }


    @RequestMapping(value = "/modify-rate", method = RequestMethod.POST)
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

    @RequestMapping(value = "/modify-rate", method = RequestMethod.GET)
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

    @RequestMapping(value = "/modify-rate/change-rateResult", method = RequestMethod.POST)
    public String changeRateResult(@RequestParam Integer raceId, @RequestParam Integer rateId, @RequestParam Integer rateResult,
                                   RedirectAttributes redirectAttributes, SessionStatus sessionStatus) {
        Rate rate = rateService.getRate(rateId);
        rate.setRateResult(referenceService.getRateResult(rateResult));
        rateService.pushRate(rate);
        String message = String.format("Результат ставки '%s' изменен на '%s'", rate.getRate(), rate.getRateResult().getRateResult());
        redirectAttributes.addAttribute("message", message);
        sessionStatus.setComplete();
        return "redirect:/modify-race?id=" + "/modify-race?id=" + raceId;
    }

}

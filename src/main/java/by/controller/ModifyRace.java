package by.controller;

import by.entity.Race;
import by.entity.Rate;
import by.entity.RateResult;
import by.service.RaceService;
import by.service.RateService;
import by.service.reference.ReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

/**
 * Контроллер для отображения страницы модификации race.
 *
 * @author Serg Shankunas
 */
@Controller
@RequestMapping("/modify-race")
public class ModifyRace {

    private RaceService raceService;

    private RateService rateService;

    private ReferenceService referenceService;

    @Autowired
    public ModifyRace(RaceService raceService, RateService rateService, ReferenceService referenceService) {
        this.raceService = raceService;
        this.rateService = rateService;
        this.referenceService = referenceService;
    }

    @ModelAttribute(value = "race")
    public Race newRequest(@RequestParam(required = false) Integer id) {
        return  (id!= null) ? raceService.getRace(id) : new Race();
    }

    @ModelAttribute(value = "rates")
    public List<Rate> getRates(@RequestParam(required = false) Integer id) {
        return  (id != null) ? rateService.getRates(id) : Collections.EMPTY_LIST;
    }

    @ModelAttribute(value = "rateResults")
    public List<RateResult> getRateResults() {
        return  referenceService.getRateResults();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doPost(Race race, RedirectAttributes redirectAttributes, SessionStatus sessionStatus) {
        String message;
        if (race == null || rateService.isAllNewRates(race.getId())) {
            raceService.pushRace(race);
            message = String.format("Забег %s сохранён", race.getId());
        } else {
            message = String.format("Невозможно сохранить забег %s. В забеге есть сыгранные ставки", race.getId());
        }
        redirectAttributes.addAttribute("message", message);
        sessionStatus.setComplete();
        return "redirect:/view-race";
    }

    @RequestMapping(method = RequestMethod.GET)
    public void doGet() {
    }

 /*   protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
    }*/

}

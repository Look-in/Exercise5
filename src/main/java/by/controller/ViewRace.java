package by.controller;

import by.Utils.RaceSortUtil;
import by.entity.Race;
import by.service.RaceService;
import by.service.reference.AttributeToCompare;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Контроллер для передачи списка забегов.
 *
 * @author Serg Shankunas
 */
@Controller
@RequestMapping("/view-race")
public class ViewRace {

    private RaceService raceService;

    @Autowired
    public ViewRace(RaceService raceService) {
        this.raceService = raceService;
    }

    @ModelAttribute(value = "races")
    public List<Race> newRequest(@RequestParam(required = false) AttributeToCompare sortingBy) {
        List<Race> races = raceService.getRaces();
        if (sortingBy != null) {
            RaceSortUtil.compare(races,sortingBy);
        }
        return races;
    }

    @ModelAttribute(value = "sortBy")
    public AttributeToCompare[] newRequestAttr() {
        return AttributeToCompare.values();
    }

    @RequestMapping(method = RequestMethod.GET)
    public void doGet() {
    }

    @RequestMapping(method = RequestMethod.POST)
    public void doPost() {
    }

}

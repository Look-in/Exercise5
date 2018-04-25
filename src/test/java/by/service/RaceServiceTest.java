package by.service;

import by.entity.Race;
import by.entity.Rate;
import by.entity.RateResult;
import by.entity.User;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class RaceServiceTest {

   private Weld weld;

    private RaceService raceService;

    private UserService userService;

    private RateService rateService;

    private void init() {
        weld = new Weld();
        WeldContainer container = weld.initialize();
        raceService = container.instance().select(RaceService.class).get();
        rateService = container.instance().select(RateService.class).get();
        userService = container.instance().select(UserService.class).get();
    }

    private void start() {
        System.out.println(userService.getRoles());
        System.out.println(raceService.getRaces().toString());
        User user = userService.checkPasswordAndGetUser("user","user");
        System.out.println(user.toString());
 /*       System.out.println(rateService.getRates().toString());
        Race race = new Race();
        race.setId(1);
        race.setRace("Забег тестовый");
        raceService.pushRace(race);*/


     /*   Rate rate = new Rate();
        RateResult rateResult = new RateResult();
        rateResult.setRateResult("Будет играть");
        rateResult.setId(2);
        rate.setId(1);
        rate.setRate("Новая ставка 2 к 1");
        rate.setRateResult(rateResult);
        rate.setRace(race);
        //rate.setFoo(20);
        rateService.pushRate(rate);*/
    }

    private void shutdown() {
        weld.shutdown();
    }

    public static void main(String[] args) {
        RaceServiceTest raceServiceTest = new RaceServiceTest();
        raceServiceTest.init();
        raceServiceTest.start();
        raceServiceTest.shutdown();
    }
}

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
    	System.out.println("Тест");
     //   System.out.println(userService.getRoles());
     //   System.out.println(raceService.getRaces().toString());
         Long timeJsp = System.currentTimeMillis();
        User user = null;
        User user1 = null;
        User user2 = null;
        for (int i = 0; i < 1; i++) {
            user = userService.checkPasswordAndGetUser("user","user");
            user1 = userService.checkPasswordAndGetUser("user1","user1");
            user2 = userService.checkPasswordAndGetUser("user2","user2");
        }
        float delta = (float)(System.currentTimeMillis() - timeJsp)/1000;
        System.out.println(delta);
        System.out.println(user.getRates());
        System.out.println(user.toString());
        if (user1 !=null)
        System.out.println(user1.toString());
        if (user2 !=null)
        System.out.println(user2.toString());
         //System.out.println(user.toString());
       
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

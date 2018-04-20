package by.service;

import by.dao.RaceDao;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class RaceServiceTest {

    private Weld weld;
    private WeldContainer container;

    RaceDao raceDao;

    UserService userService;

    private void init() {
        weld = new Weld();
        container = weld.initialize();
        raceDao = container.instance().select(RaceDao.class).get();
        userService = container.instance().select(UserService.class).get();
    }

    private void start() {
        System.out.println(userService.getRoles());
        System.out.println(userService.getUser("user"));
        System.out.println(raceDao.getRaces().toString());
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

package by.service;

import by.dao.RaceDao;
import by.entity.Race;

import javax.inject.Inject;
import java.util.List;

/**
 * Реализация {@link RaceService}.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
public class RaceServiceImpl implements RaceService {

    @Inject
    private RaceDao raceDao;

    @Override
    public List<Race> getRaces() {
        return raceDao.getRaces();
    }

    @Override
    public Race getRace(int raceId) {
        return raceDao.getRace(raceId);
    }

    @Override
    public void pushRace(Race race) {
        if (race.getId() == null) {
            raceDao.create(race);
        } else {
            raceDao.update(race);
        }
    }

    @Override
    public void deleteRace(int raceId) {
        raceDao.delete(raceId);
    }
}

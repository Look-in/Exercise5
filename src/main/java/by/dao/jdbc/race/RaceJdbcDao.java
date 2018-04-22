package by.dao.jdbc.race;

import by.dao.RaceDao;
import by.dao.jdbc.BaseJdbcDao;
import by.entity.Race;
import by.entity.Rate;

import java.util.List;

public class RaceJdbcDao extends BaseJdbcDao implements RaceDao {

    @Override
    public List<Race> getRaces() {
        return findAll(Race.class);
    }

    @Override
    public Race getRace(int raceId) {
        return find(Race.class, raceId);
    }
}

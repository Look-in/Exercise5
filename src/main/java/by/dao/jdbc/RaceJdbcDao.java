package by.dao.jdbc;

import by.dao.RaceDao;
import by.entity.Race;

import java.util.List;

public class RaceJdbcDao extends BaseJdbcDao implements RaceDao {

    @Override
    @SuppressWarnings("unchecked")
    public List<Race> getRaces() {
        String sql = "SELECT * FROM RACE;";
        return getEntityManager().findAll(sql, Race.class);
    }

    @Override
    public Race getRace(int raceId) {
        return null;
    }
}

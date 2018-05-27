package by.dao.jdbc.race;

import by.dao.RaceDao;
import by.dao.jdbc.BaseJdbcDao;
import by.entity.Race;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Реализация {@link RaceDao}.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
@Repository
public class RaceJdbcDao extends BaseJdbcDao implements RaceDao{

    @Override
    public List<Race> getRaces() {
        return findAll(Race.class);
    }

    @Override
    public Race getRace(int raceId) {
        return find(Race.class, raceId);
    }

    @Override
    public void create(Race entity) {
        persist(Race.class, entity);
    }

    @Override
    public void update(Race entity) {
        merge(Race.class, entity);
    }

    @Override
    public void delete(Integer id) {
        remove(Race.class, id);
    }
}

package by.dao.jdbc.race;

import by.dao.RateDao;
import by.dao.jdbc.BaseJdbcDao;
import by.entity.Rate;

import java.util.List;

public class RateJdbcDao extends BaseJdbcDao implements RateDao {

    @Override
    public List<Rate> getRates() {
        return findAll(Rate.class);
    }

    @Override
    public Rate getRate(int rateId) {
        return find(Rate.class, rateId);
    }

    @Override
    public int getRateResultOfRate(int rateId) {
        return find("select result_id from rate where id = ?;", Integer.class, rateId);
    }

    @Override
    public void create(Rate entity) {
        persist(Rate.class, entity);
    }

    @Override
    public void update(Rate entity) {
        merge(Rate.class, entity);
    }

    @Override
    public void delete(Integer id) {
        remove(Rate.class, id);
    }
}

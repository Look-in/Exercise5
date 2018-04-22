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
}

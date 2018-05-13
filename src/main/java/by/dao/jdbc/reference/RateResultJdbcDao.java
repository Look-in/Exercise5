package by.dao.jdbc.reference;

import by.dao.RateResultDao;
import by.dao.jdbc.BaseJdbcDao;
import by.entity.RateResult;

import java.util.List;

public class RateResultJdbcDao extends BaseJdbcDao implements RateResultDao {

    @Override
    public List<RateResult> getRateResults() {
        return findAll(RateResult.class);
    }

    @Override
    public RateResult getRateResult(int rateResultId) {
        return find(RateResult.class, rateResultId);
    }
}

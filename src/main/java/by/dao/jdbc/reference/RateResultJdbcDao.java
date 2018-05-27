package by.dao.jdbc.reference;

import by.dao.RateResultDao;
import by.dao.jdbc.BaseJdbcDao;
import by.entity.RateResult;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Реализация {@link RateResultDao}.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
public class RateResultJdbcDao extends BaseJdbcDao {
/*
    @Override
    public List<RateResult> getRateResults() {
        return findAll(RateResult.class);
    }

    @Override
    public RateResult getRateResult(int rateResultId) {
        return find(RateResult.class, rateResultId);
    }*/
}

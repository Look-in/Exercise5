package by.service.reference;

import by.dao.RateResultDao;
import by.entity.RateResult;

import javax.inject.Inject;
import java.util.List;

/**
 * Реализация {@link ReferenceService}.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
public class ReferenceServiceImpl implements ReferenceService {

    @Inject
    RateResultDao rateResultDao;

    @Override
    public List<RateResult> getRateResults() {
        return rateResultDao.getRateResults();
    }

    @Override
    public RateResult getRateResult(int id) {
        return rateResultDao.getRateResult(id);
    }
}

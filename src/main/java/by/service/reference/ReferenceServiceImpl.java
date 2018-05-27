package by.service.reference;

import by.dao.RateResultDao;
import by.entity.RateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Реализация {@link ReferenceService}.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
@Service
public class ReferenceServiceImpl implements ReferenceService {

    private RateResultDao rateResultDao;

    @Autowired
    public ReferenceServiceImpl(RateResultDao rateResultDao) {
        this.rateResultDao = rateResultDao;
    }

    @Override
    public List<RateResult> getRateResults() {
        return rateResultDao.getRateResults();
    }

    @Override
    public RateResult getRateResult(int id) {
        return rateResultDao.getRateResult(id);
    }
}

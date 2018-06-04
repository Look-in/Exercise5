package by.service;

import by.dao.RaceDao;
import by.dao.RateDao;
import by.dao.RateResultDao;
import by.entity.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Реализация {@link RateService}.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
@Service
public class RateServiceImpl implements RateService {

    private RateDao rateDao;

    private RateResultDao rateResult;

    private RaceDao raceDao;

    @Autowired
    public RateServiceImpl(RateDao rateDao, RateResultDao rateResult, RaceDao raceDao) {
        this.rateDao = rateDao;
        this.rateResult = rateResult;
        this.raceDao = raceDao;
    }

    @Override
    public List<Rate> getRates(int id) {
        return rateDao.getRates(id);
    }

    @Override
    public boolean isAllNewRates(int raceId) {
        return rateDao.getRates(raceId).stream().filter(e -> e.getRace().getId() == raceId)
                .noneMatch(e -> e.getRateResult().getId() != 1);
    }

    @Override
    public Rate getRate(int raceId) {
        return rateDao.getRate(raceId);
    }

    @Override
    public void pushRate(Rate rate) {
        if (rate.getId() == null) {
            rateDao.create(rate);
        } else {
            rateDao.update(rate);
        }
    }

    @Override
    public void deleteRate(int rateId) {
        rateDao.delete(rateId);
    }

    @Override
    public Rate getNewRate(int raceId) {
        Rate rate = new Rate();
        /* New Rates should have result = 1 */
        rate.setRateResult(rateResult.getRateResult(1));
        rate.setRace(raceDao.getRace(raceId));
        return rate;
    }

    @Override
    public int getRateResultOfRate(int rateId) {
        return rateDao.getRateResultOfRate(rateId);
    }
}

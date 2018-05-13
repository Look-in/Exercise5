package by.service;

import by.dao.RaceDao;
import by.dao.RateDao;
import by.dao.RateResultDao;
import by.entity.Rate;
import by.entity.RateResult;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class RateServiceImpl implements RateService {

    @Inject
    private RateDao rateDao;

    @Inject
    private RateResultDao rateResult;

    @Inject
    private RaceDao raceDao;

    @Override
    public List<Rate> getRates() {
        return rateDao.getRates();
    }

    @Override
    public List<Rate> getRatesForRace(int raceId) {
        return rateDao.getRates().stream().filter(e -> e.getRace().getId() == raceId)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAllNewRates(int raceId) {
        return rateDao.getRates().stream().filter(e -> e.getRace().getId() == raceId)
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

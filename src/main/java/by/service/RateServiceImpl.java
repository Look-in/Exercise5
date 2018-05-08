package by.service;

import by.dao.RateDao;
import by.entity.Rate;

import javax.inject.Inject;
import java.util.List;

public class RateServiceImpl implements RateService {

    @Inject
    RateDao rateDao;

    @Override
    public List<Rate> getRates() {
        return rateDao.getRates();
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
}

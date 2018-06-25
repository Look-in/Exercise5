package by.service;

import by.entity.Rate;

import java.util.List;

/**
 * Интерфейс для работы со ставками.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
public interface RateService {

    List<Rate> getRates(int id);

    boolean isAllNewRates(int raceId);

    Rate getRate(int raceId);

    void pushRate(Rate rate);

    void deleteRate(int rateId);

    Rate getNewRate(int raceId);

    int getRateResultOfRate(int rateId);

    void changeRateResult(int rateId, int rateResult);
}

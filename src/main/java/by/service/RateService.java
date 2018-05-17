package by.service;

import by.entity.Rate;

import java.util.List;

public interface RateService {

    List<Rate> getRates();

    List<Rate> getRatesForRace(int raceId);

    boolean isAllNewRates(int raceId);

    Rate getRate(int raceId);

    void pushRate(Rate rate);

    void deleteRate(int rateId);

    Rate getNewRate(int raceId);

    int getRateResultOfRate(int rateId);

    boolean hasRates(int raceId);
}

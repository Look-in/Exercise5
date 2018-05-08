package by.service;

import by.entity.Rate;

import java.util.List;

public interface RateService {

    List<Rate> getRates();

    Rate getRate(int raceId);

    void pushRate(Rate rate);
}

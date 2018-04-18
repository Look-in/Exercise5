package by.dao;

import by.entity.Rate;

import java.util.List;

public interface RateDao {

    List<Rate> getRates(int raceId);

    Rate getRate(int rateId);
}

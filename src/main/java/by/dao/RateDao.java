package by.dao;

import by.entity.Rate;

import java.util.List;

public interface RateDao {

    List<Rate> getRates();

    Rate getRate(int rateId);
}

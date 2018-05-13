package by.dao;

import by.entity.Rate;

import java.util.List;

public interface RateDao  extends ChangeInstance<Rate> {

    List<Rate> getRates();

    Rate getRate(int rateId);

    int getRateResultOfRate(int rateId);
}

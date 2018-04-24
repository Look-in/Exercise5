package by.dao;

import by.entity.Rate;

import java.util.List;

public interface RateDao  extends dao.ChangeInstance<Rate> {

    List<Rate> getRates();

    Rate getRate(int rateId);
}

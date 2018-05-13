package by.dao;

import by.entity.RateResult;

import java.util.List;

public interface RateResultDao {

    List<RateResult> getRateResults();

    RateResult getRateResult(int rateResultId);
}

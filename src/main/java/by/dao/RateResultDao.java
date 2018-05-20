package by.dao;

import by.entity.RateResult;

import java.util.List;

/**
 * Интерфейс для работы с результатами ставок.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
public interface RateResultDao {

    List<RateResult> getRateResults();

    RateResult getRateResult(int rateResultId);
}

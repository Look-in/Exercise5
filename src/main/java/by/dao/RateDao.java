package by.dao;

import by.entity.Rate;

import java.util.List;

/**
 * Интерфейс для работы с Rate.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
public interface RateDao extends ChangeInstance<Rate> {

    List<Rate> getRates(int id);

    Rate getRate(int rateId);

    int getRateResultOfRate(int rateId);
}

package by.service;

import by.entity.Rate;
import by.entity.User;

/**
 * Интерфейс для работы со ставками клиентов.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
public interface UserRateService {

    void placeUserRate(User user, Rate rate);

    void replaceUserRate(User user, Rate rate);
}

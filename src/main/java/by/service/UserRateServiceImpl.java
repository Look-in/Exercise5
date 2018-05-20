package by.service;

import by.dao.UserDao;
import by.entity.Rate;
import by.entity.User;

import javax.inject.Inject;

/**
 * Реализация {@link UserRateService}.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
public class UserRateServiceImpl implements UserRateService {

    @Inject
    private UserDao userDao;

    @Override
    public void placeUserRate(User user, Rate rate) {
        user.getRates().add(rate);
        userDao.update(user);
    }

    @Override
    public void replaceUserRate(User user, Rate rate) {
        user.getRates().remove(rate);
        userDao.update(user);
    }
}

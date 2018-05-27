package by.service;

import by.dao.UserDao;
import by.entity.Rate;
import by.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Реализация {@link UserRateService}.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
@Service
public class UserRateServiceImpl implements UserRateService {

    private UserDao userDao;

    @Autowired
    public UserRateServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

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

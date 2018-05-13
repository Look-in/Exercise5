package by.dao;

import by.entity.User;

public interface UserDao {

    User getUser(String userName);

    User getUserRates(User user);
}

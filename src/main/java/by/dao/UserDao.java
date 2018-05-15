package by.dao;

import by.entity.User;

public interface UserDao extends ChangeInstance<User> {

    User getUser(String userName);
}

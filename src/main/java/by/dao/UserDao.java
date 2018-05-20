package by.dao;

import by.entity.User;

/**
 * Интерфейс для работы с пользователями.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
public interface UserDao extends ChangeInstance<User> {

    User getUser(String userName);
}

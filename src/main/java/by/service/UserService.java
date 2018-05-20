package by.service;

import by.entity.Role;
import by.entity.User;

import java.util.List;

/**
 * Интерфейс для работы с пользователями.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
public interface UserService {

    User checkPasswordAndGetUser(String userName, String password);

    User getUser(String userName);

    Role getRole(int roleId);

    List<Role> getRoles();
}

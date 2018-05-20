package by.dao;

import by.entity.Role;

import java.util.List;

/**
 * Интерфейс для работы с ролями пользователей.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
public interface UserRoleDao {

    Role getRole(int roleId);

    List<Role> getRoles();
}

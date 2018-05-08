package by.dao;

import by.entity.Role;

import java.util.List;

public interface UserRoleDao {

    Role getRole(int roleId);

    List<Role> getRoles();
}

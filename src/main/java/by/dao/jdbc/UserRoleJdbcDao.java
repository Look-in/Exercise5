package by.dao.jdbc;

import by.dao.UserRoleDao;
import by.entity.Role;

import java.util.List;

public class UserRoleJdbcDao extends BaseJdbcDao implements UserRoleDao {

    @Override
    public Role getRole(int roleId) {
        //final String SQL = "SELECT * FROM Roles WHERE id=?;";
        return  find(Role.SQL, Role.class, roleId);
    }

    @Override
    public List<Role> getRoles() {
        final String SQL = "SELECT * FROM ROLES;";
        return findAll(SQL, Role.class);
    }
}

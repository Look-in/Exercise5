package by.dao.jdbc;

import by.dao.UserRoleDao;
import by.entity.Role;

import java.util.List;

public class UserRoleJdbcDao extends BaseJdbcDao implements UserRoleDao {

    @Override
    @SuppressWarnings("unchecked")
    public Role getRole(int roleId) {
        final String SQL = "SELECT * FROM Roles WHERE id=?;";
        return  (Role) getEntityManager().find(SQL, Role.class, roleId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Role> getRoles() {
        final String SQL = "SELECT * FROM ROLES;";
        return getEntityManager().findAll(SQL, Role.class);
    }
}

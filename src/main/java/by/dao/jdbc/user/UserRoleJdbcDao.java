package by.dao.jdbc.user;

import by.dao.UserRoleDao;
import by.dao.jdbc.BaseJdbcDao;
import by.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Реализация {@link UserRoleDao}.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
@Repository
public class UserRoleJdbcDao extends BaseJdbcDao implements UserRoleDao {

    @Override
    public Role getRole(int roleId) {
        return find(Role.class, roleId);
    }

    @Override
    public List<Role> getRoles() {
        return findAll(Role.class);
    }
}

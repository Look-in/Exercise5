package by.dao.jdbc;

import by.dao.UserDao;
import by.entity.User;

public class UserJdbcDao extends BaseJdbcDao implements UserDao {

    @Override
    @SuppressWarnings("unchecked")
    public User getUser(String userName) {
        final String SQL = "SELECT * FROM users WHERE username=?;";
        //user.setRole(userRoleDao.getRole(rs.getInt("role_id")));
        return (User) getEntityManager().find(SQL, User.class, userName);
    }
}

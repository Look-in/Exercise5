package by.dao.jdbc;

import by.dao.UserDao;
import by.entity.User;

public class UserJdbcDao extends BaseJdbcDao implements UserDao {

    @Override
    public User getUser(String userName) {
        final String SQL = "SELECT * FROM users WHERE username=?;";
        return find(SQL, User.class, userName);
    }
}

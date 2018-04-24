package by.dao.jdbc.user;

import by.dao.UserDao;
import by.dao.jdbc.BaseJdbcDao;
import by.entity.User;

public class UserJdbcDao extends BaseJdbcDao implements UserDao {

    @Override
    public User getUser(String userName) {
        String sql = "SELECT * FROM users WHERE username=?;";
        return find(sql, User.class, userName);
    }

    @Override
    public User getUserRates(User user) {
     return getProxy(User.class);
    }
}

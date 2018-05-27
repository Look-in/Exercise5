package by.service;

import by.dao.UserDao;
import by.dao.UserRoleDao;
import by.entity.Role;
import by.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Реализация {@link UserService}.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRoleDao userRoleDao;

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserRoleDao userRoleDao, UserDao userDao) {
        this.userRoleDao = userRoleDao;
        this.userDao = userDao;
    }

    @Override
    public Role getRole(int roleId) {
        return userRoleDao.getRole(roleId);
    }


    @Override
    public List<Role> getRoles() {
        return userRoleDao.getRoles();
    }

    @Override
    public User checkPasswordAndGetUser(String userName, String password) {
        User user = userDao.getUser(userName);
        return (user != null && user.getPassword().equals(password)) ? user :
                null;
    }

    @Override
    public User getUser(String userName) {
        return userDao.getUser(userName);
    }
}

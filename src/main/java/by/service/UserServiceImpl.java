package by.service;

import by.dao.UserDao;
import by.dao.UserRoleDao;
import by.entity.Role;
import by.entity.User;

import javax.inject.Inject;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Inject
    private UserRoleDao userRoleDao;

    @Inject
    private UserDao userDao;

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
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public User getUser(String userName) {
        return userDao.getUser(userName);
    }
}

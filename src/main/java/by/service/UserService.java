package by.service;

import by.entity.Role;
import by.entity.User;

import java.util.List;

public interface UserService {

    List<Role> getRoles();

    Role getRole(int roleId);

    User checkPasswordAndGetUser(String userName, String password);

    User getUserRates(User user);
}

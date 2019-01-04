package ru.ldwx.accounting.util;

import ru.ldwx.accounting.model.Role;
import ru.ldwx.accounting.model.User;
import ru.ldwx.accounting.to.UserTo;

public class UserUtil {

    public static final int DEFAULT_SUM_PER_DAY = 2000;

    public static User createNewFromTo(UserTo newUser) {
        return new User(null, newUser.getName(), newUser.getEmail(), newUser.getPassword(), Role.ROLE_USER);
    }

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getSumPerDay());
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }
}
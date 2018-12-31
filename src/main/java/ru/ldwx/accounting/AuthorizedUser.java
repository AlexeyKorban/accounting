package ru.ldwx.accounting;

import ru.ldwx.accounting.model.User;
import ru.ldwx.accounting.to.UserTo;
import ru.ldwx.accounting.util.UserUtil;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    private static long serialVersionUID = 1L;

    private UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(),user.isEnabled(), true,  true, true, user.getRoles());
        this.userTo = UserUtil.asTo(user);
    }

    public int getId() {
        return userTo.getId();
    }

    public void update(UserTo newTo) {
        userTo = newTo;
    }

    public UserTo getUserTo() {
        return userTo;
    }

    @Override
    public String toString() {
        return userTo.toString();
    }
}

package ru.ldwx.accounting.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.ldwx.accounting.AuthorizedUser;
import ru.ldwx.accounting.model.AbstractBaseEntity;

import static java.util.Objects.requireNonNull;
import static ru.ldwx.accounting.util.UserUtil.DEFAULT_SUM_PER_DAY;

public class SecurityUtil {

    private SecurityUtil() {
    }

    public static AuthorizedUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
    }

    public static AuthorizedUser get() {
        AuthorizedUser user = safeGet();
        requireNonNull(user, "No authorised user found");
        return user;
    }

    public static int authUserId() {
        return get().getUserTo().getId();
    }

    public static int authUserSumPerDay() {
        return get().getUserTo().getSumPerDay();
    }
}

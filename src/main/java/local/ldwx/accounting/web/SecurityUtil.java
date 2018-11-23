package local.ldwx.accounting.web;

import local.ldwx.accounting.model.AbstractBaseEntity;

import static local.ldwx.accounting.util.ProjectsUtil.DEFAULT_SUM_PER_DAY;

public class SecurityUtil {

    private static int id = AbstractBaseEntity.START_SEQ;

    private SecurityUtil() {
    }

    public static int authUserId() {
        return id;
    }

    public static void setAuthUserId(int id) {
        SecurityUtil.id = id;
    }

    public static int authUserSumPerDay() {
        return DEFAULT_SUM_PER_DAY;
    }
}

package local.ldwx.accounting.web;

import static local.ldwx.accounting.util.ProjectsUtil.DEFAULT_SUM_PER_DAY;

public class SecurityUtil {

    private static int id = 1;

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

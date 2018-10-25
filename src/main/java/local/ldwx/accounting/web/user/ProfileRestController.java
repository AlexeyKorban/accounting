package local.ldwx.accounting.web.user;

import local.ldwx.accounting.model.User;
import org.springframework.stereotype.Controller;

import static local.ldwx.accounting.web.SecurityUtil.authUserId;

@Controller
public class ProfileRestController extends AbstractUserController{

    public User get() {
        return super.get(authUserId());
    }

    public void delete() {
        super.delete(authUserId());
    }

    public void update(User user) {
        super.update(user, authUserId());
    }
}

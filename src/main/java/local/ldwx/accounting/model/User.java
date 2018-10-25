package local.ldwx.accounting.model;

import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

import static local.ldwx.accounting.util.ProjectsUtil.DEFAULT_SUM_PER_DAY;

public class User extends AbstractNamedEntity{

    private String email;

    private String password;

    private boolean enabled = true;

    private Date registered = new Date();

    private Set<Role> roles;

    private int sumPerDay = DEFAULT_SUM_PER_DAY;

    public User(Integer id, String name, String email, String password, boolean enabled, Set<Role> roles, int sumPerDay) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
        this.sumPerDay = sumPerDay;
    }

    public User(Integer id, String name, String email, String password, Role role, Role... roles) {
        this(id, name, email, password, true, EnumSet.of(role, roles), DEFAULT_SUM_PER_DAY);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public int getSumPerDay() {
        return sumPerDay;
    }

    public void setSumPerDay(int sumPerDay) {
        this.sumPerDay = sumPerDay;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email +
                ", id='" + id +
                ", enabled=" + enabled +
                ", registered=" + registered +
                ", roles=" + roles +
                ", sumPerDay=" + sumPerDay +
                '}';
    }
}

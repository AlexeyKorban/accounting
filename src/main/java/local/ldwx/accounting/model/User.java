package local.ldwx.accounting.model;

import org.springframework.util.CollectionUtils;

import java.util.*;

import static local.ldwx.accounting.util.ProjectsUtil.DEFAULT_SUM_PER_DAY;

public class User extends AbstractNamedEntity{

    private String email;

    private String password;

    private boolean enabled = true;

    private Date registered = new Date();

    private Set<Role> roles;

    private int sumPerDay = DEFAULT_SUM_PER_DAY;

    public User() {}

    public User(Integer id, String name, String email, String password, int sumPerDay, boolean enabled, Date registered, Collection<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.sumPerDay = sumPerDay;
        this.enabled = enabled;
        this.registered = registered;
        setRoles(roles);
    }

    public User(Integer id, String name, String email, String password, Role role, Role... roles) {
        this(id, name, email, password, DEFAULT_SUM_PER_DAY, true, new Date(), EnumSet.of(role, roles));
    }

    public User(User u) {
        this(u.getId(), u.getName(), u.getEmail(), u.getPassword(), u.getSumPerDay(), u.isEnabled(), u.getRegistered(), u.getRoles());
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

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? Collections.emptySet() : EnumSet.copyOf(roles);
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

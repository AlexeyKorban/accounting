package local.ldwx.accounting.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Project.UPDATE, query = "UPDATE Project p SET p=:p WHERE p.user.id=userId"),
        @NamedQuery(name = Project.FIND, query = "SELECT p FROM Project p WHERE p.id=:id and p.user.id=:userId"),
        @NamedQuery(name = Project.DELETE, query = "DELETE FROM Project p WHERE p.id=:id and p.user.id=:userId"),
        @NamedQuery(name = Project.ALL_SORTED, query = "SELECT p FROM Project p WHERE p.user.id=:userId ORDER BY p.dateTime DESC "),
        @NamedQuery(name = Project.ALL_BETWEEN, query = "SELECT p FROM Project p WHERE p.user.id=:userId AND p.dateTime BETWEEN ?2 AND ?3 ORDER BY p.dateTime DESC"),
})
@Entity
@Table(name = "projects", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "projects_unique_user_datetime_idx"))
public class Project extends AbstractBaseEntity {

    public static final String FIND = "Project.find";
    public static final String UPDATE = "Project.update";
    public static final String DELETE = "Project.delete";
    public static final String ALL_BETWEEN = "Project.getAllBetween";
    public static final String ALL_SORTED = "Project.getAllSorted";

    @Column(name = "date_time", columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDateTime dateTime;

    @Column(name ="description", nullable = false)
    private String description;

    @Column(name = "sum")
    @Range(min = 1, max = 10000)
    private int sum;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Project() {
    }

    public Project(Integer id, LocalDateTime dateTime, String description, int summ) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.sum = summ;
    }

    public Project(LocalDateTime dateTime, String description, int summ) {
        this(null, dateTime, description, summ);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getSum() {
        return sum;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Project{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", sum=" + sum +
                ", id=" + id +
                '}';
    }
}
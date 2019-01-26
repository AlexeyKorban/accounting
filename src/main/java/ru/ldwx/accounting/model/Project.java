package ru.ldwx.accounting.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;
import ru.ldwx.accounting.View;
import ru.ldwx.accounting.util.DateTimeUtil;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Project.ALL_SORTED, query = "SELECT p FROM Project p WHERE p.user.id=:userId ORDER BY p.dateTime DESC"),
        @NamedQuery(name = Project.DELETE, query = "DELETE FROM Project p WHERE p.id=:id AND p.user.id=:userId"),
        @NamedQuery(name = Project.GET_BETWEEN, query = "SELECT p FROM Project p " +
                "WHERE p.user.id=:userId AND p.dateTime BETWEEN :startDate AND :endDate ORDER BY p.dateTime DESC"),
})
@Entity
@Table(name = "projects", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "projects_unique_user_datetime_idx")})
public class Project extends AbstractBaseEntity {
    public static final String ALL_SORTED = "Project.getAll";
    public static final String DELETE = "Project.delete";
    public static final String GET_BETWEEN = "Project.getBetween";

    @Column(name = "date_time", nullable = false)
    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    @SafeHtml(groups = {View.Web.class})
    private String description;

    @Column(name = "sum", nullable = false)
    @Range(min = 10, max = 5000)
    @NotNull
    private Integer sum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(groups = View.Persist.class)
    private User user;

    public Project() {
    }

    public Project(LocalDateTime dateTime, String description, int sum) {
        this(null, dateTime, description, sum);
    }

    public Project(Integer id, LocalDateTime dateTime, String description, int sum) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.sum = sum;
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

    public void setSum(Integer sum) {
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

package local.ldwx.accounting.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Project extends AbstractBaseEntity {
    private final LocalDateTime dateTime;

    private final String description;

    private final int sum;

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

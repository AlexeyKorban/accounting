package local.ldwx.accounting.to;

import java.time.LocalDateTime;

public class ProjectTo {
    private final Integer id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int sum;

    private final boolean excess;

    public ProjectTo(Integer id, LocalDateTime dateTime, String description, int sum, boolean excess) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.sum = sum;
        this.excess = excess;
    }

    public Integer getId() {
        return id;
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

    public boolean isExcess() {
        return excess;
    }

    @Override
    public String toString() {
        return "ProjectTo{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", sum=" + sum +
                ", excess=" + excess +
                '}';
    }
}

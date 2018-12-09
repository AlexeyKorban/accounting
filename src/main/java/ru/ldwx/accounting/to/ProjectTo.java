package ru.ldwx.accounting.to;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectTo projectTo = (ProjectTo) o;
        return sum == projectTo.sum &&
                excess == projectTo.excess &&
                Objects.equals(id, projectTo.id) &&
                Objects.equals(dateTime, projectTo.dateTime) &&
                Objects.equals(description, projectTo.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime, description, sum, excess);
    }
}

package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Meal extends AbstractBaseEntity {

    private final Integer userId;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public Meal(Integer userID, LocalDateTime dateTime, String description, int calories) {
        this(null, userID, dateTime, description, calories);
    }

    public Meal(Integer id, Integer userId, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.id = id;
        this.userId = userId;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Integer getUserID() {
        return userId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }


    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", userID=" + userId +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}

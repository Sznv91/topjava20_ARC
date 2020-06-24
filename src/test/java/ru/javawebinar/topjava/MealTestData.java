package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int NOT_FOUND = 10;
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static Meal meal2 = new Meal(100002, LocalDateTime.of(2020, 6, 21, 12, 47), "first user; first meal", 998);
    public static Meal meal3 = new Meal(100003, LocalDateTime.of(2020, 6, 21, 23, 59), "first user; second meal", 999);
    public static Meal meal4 = new Meal(100004, LocalDateTime.of(2020, 6, 22, 0, 0), "first user; third meal", 999);
    public static Meal meal5 = new Meal(100005, LocalDateTime.of(2020, 6, 22, 23, 59), "first user; fourth meal", 999);

    public static Meal meal6 = new Meal(100006, LocalDateTime.of(2020, 6, 21, 12, 45), "second user; first meal", 997);
    public static Meal meal7 = new Meal(100007, LocalDateTime.of(2020, 6, 21, 23, 59), "second user; second meal", 996);
    public static Meal meal8 = new Meal(100008, LocalDateTime.of(2020, 6, 22, 0, 0), "second user; third meal", 996);
    public static Meal meal9 = new Meal(100009, LocalDateTime.of(2020, 6, 22, 23, 59), "second user; fourth meal", 996);

    public static List<Meal> getBetweenUserList() {
        return Arrays.asList(meal3, meal2);
    }

    public static List<Meal> getAdminList() {
        return Arrays.asList(meal9, meal8, meal7, meal6);
    }
}

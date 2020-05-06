package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumCaloriesPerDay = new HashMap<>();
        meals.forEach(meal -> sumCaloriesPerDay.merge(localDateConverter(meal.getDateTime()), meal.getCalories(), Integer::sum));
        List<UserMealWithExcess> result = new ArrayList<>();
        for (UserMeal meal : meals) {
            LocalTime currentMealTime = localTimeConverter(meal.getDateTime());
            if (currentMealTime.isAfter(startTime) && currentMealTime.isBefore(endTime)) {
                boolean excess = sumCaloriesPerDay.get(localDateConverter(meal.getDateTime())) > caloriesPerDay;
                result.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess));
            }
        }
        return result;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumCaloriesPerDay = new HashMap<>();
        meals.forEach(meal -> sumCaloriesPerDay.merge(localDateConverter(meal.getDateTime()), meal.getCalories(), Integer::sum));
        List<UserMealWithExcess> result = new ArrayList<>();
        meals.stream()
                .filter(userMeal -> localTimeConverter(userMeal.getDateTime()).isAfter(startTime)
                        && localTimeConverter(userMeal.getDateTime()).isBefore(endTime))
                .forEach(userMeal -> result.add(new UserMealWithExcess(
                        userMeal.getDateTime()
                        , userMeal.getDescription()
                        , userMeal.getCalories()
                        , (sumCaloriesPerDay.get(localDateConverter(userMeal.getDateTime())) > caloriesPerDay))));
        return result;
    }

    private static LocalDate localDateConverter(LocalDateTime date) {
        int year = date.getYear();
        Month mount = date.getMonth();
        int day = date.getDayOfMonth();
        return LocalDate.of(year, mount, day);
    }

    private static LocalTime localTimeConverter(LocalDateTime date) {
        int hour = date.getHour();
        int minute = date.getMinute();
        return LocalTime.of(hour, minute);
    }
}

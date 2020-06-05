package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsUtil {

    public static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

    public static List<MealTo> mergeMeals(List<Meal> mealList, List<MealTo> mealToList) {
        List<MealTo> result = new ArrayList<>();
        for (Meal meal : mealList) {
            for (MealTo mealTo : mealToList) {
                if (mealTo.getDescription().equals(meal.getDescription()) ||
                        mealTo.getCalories() == meal.getCalories() ||
                        mealTo.getDateTime().equals(meal.getDateTime())) {
                    result.add(mealTo);
                } else {
                    result.add(new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), false));
                }
            }
        }
        return result;
    }
}

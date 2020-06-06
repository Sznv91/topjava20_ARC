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

    public static List<MealTo> merge(List<Meal> mealList, List<MealTo> mealToList) {
        List<MealTo> result = new ArrayList<>();
        mealList.forEach(meal -> result.add(new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), false)));
        List<MealTo> listToDelete = new ArrayList<>();
        mealToList.forEach(mealTo -> listToDelete.add(new MealTo(mealTo.getDateTime(), mealTo.getDescription(), mealTo.getCalories(), false)));
        result.removeAll(listToDelete);
        result.addAll(mealToList);
        return result;
    }
}

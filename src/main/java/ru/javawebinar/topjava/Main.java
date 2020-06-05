package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsData;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.List;

/**
 * @see <a href="http://topjava.herokuapp.com">Demo application</a>
 * @see <a href="https://github.com/JavaOPs/topjava">Initial project</a>
 */
public class Main {
    public static void main(String[] args) {
        MealsData data = new MealsData();
        List<MealTo> mealsTo = MealsUtil.filteredByStreams(data.getMealsList(), LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
    }
}

package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapMealStorage implements MealStorage {
    private int counter = 0;
    private Map<Integer, Meal> storage = new ConcurrentHashMap<>();

    public MapMealStorage() {
        List<Meal> meals = Arrays.asList(
                new Meal(0, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(0, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(0, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(0, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(0, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(0, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(0, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        meals.forEach(this::create);
    }

    @Override
    public Meal get(int uuid) {
        return storage.get(uuid);
    }

    @Override
    public Meal create(Meal meal) {
        Meal mealToWrite = new Meal(++counter, meal.getDateTime(), meal.getDescription(), meal.getCalories());
        return doAdd(mealToWrite);
    }

    @Override
    public void remove(int uuid) {
        storage.remove(uuid);
    }

    @Override
    public Meal update(Meal meal) {
        if (doAdd(meal) != null){
            return meal;
        } else {
            return null;
        }
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }

    private Meal doAdd(Meal meal) {
        return storage.put(meal.getId(), meal);
    }
}

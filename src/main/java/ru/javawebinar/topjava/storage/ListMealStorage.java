package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static ru.javawebinar.topjava.util.MealsUtil.generateUUID;

public class ListMealStorage implements MealStorage {
    private List<Meal> storage = new CopyOnWriteArrayList<>();

    @Override
    public Meal get(String uuid) {
        for (Meal meal : storage) {
            if (meal.getId().equals(uuid)) {
                return meal;
            }
        }
        return null;
    }

    @Override
    public Meal create(Meal meal) {
        storage.add(meal);
        return meal;
    }

    @Override
    public void remove(String uuid) {
        storage.remove(get(uuid));
    }

    @Override
    public Meal update(Meal meal) {
        if (get(meal.getId()) != null) {
            remove(meal.getId());
            create(meal);
            return meal;
        } else {
            return null;
        }
    }

    @Override
    public List<Meal> getAll() {
        return new CopyOnWriteArrayList<>(storage);
    }

    @Override
    public void addList(List<Meal> mealList) {
        storage.addAll(mealList);
    }

    @Override
    public void fillPredefined() {
        List<Meal> meals = Arrays.asList(
                new Meal(generateUUID(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(generateUUID(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(generateUUID(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(generateUUID(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(generateUUID(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(generateUUID(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(generateUUID(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        addList(meals);
    }

}

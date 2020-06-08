package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapMealStorage implements MealStorage {
    private Map<Integer, Meal> storage = new ConcurrentHashMap<>();

    @Override
    public Meal get(int uuid) {
        return storage.get(uuid);
    }

    @Override
    public Meal create(LocalDateTime dateTime, String description, int calories) {
        Meal meal = new Meal(1 + (int) (Math.random() * 10000), dateTime, description, calories);
        storage.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void remove(int uuid) {
        storage.remove(uuid);
    }

    @Override
    public Meal update(int id, LocalDateTime dateTime, String description, int calories) {
        if (get(id) != null) {
            Meal meal = new Meal(id, dateTime, description, calories);
            storage.replace(id, meal);
            return meal;
        }
        return null;
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }
}

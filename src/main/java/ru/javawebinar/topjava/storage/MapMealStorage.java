package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class MapMealStorage implements MealStorage {
    private Map<String, Meal> storage = new ConcurrentHashMap<>();

    @Override
    public Meal get(String uuid) {
        return storage.get(uuid);
    }

    @Override
    public Meal create(Meal meal) {
        storage.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void addList(List<Meal> mealList) {
        mealList.forEach(this::create);
    }

    @Override
    public void remove(String uuid) {
        storage.remove(uuid);
    }

    @Override
    public Meal update(Meal meal) {
        if (get(meal.getId()) != null){
            remove(meal.getId());
            create(meal);
            return meal;
        }
        return null;
    }

    @Override
    public List<Meal> getAll() {
        return new CopyOnWriteArrayList<>(storage.values());
    }
}

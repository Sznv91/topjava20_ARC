package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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

}

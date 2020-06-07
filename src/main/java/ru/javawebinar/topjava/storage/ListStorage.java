package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class ListStorage implements Storage {

    private List<Meal> storage = new ArrayList<>();

    @Override
    public Meal get(String uuid) {
        for (Meal meal : storage) {
            if (meal.getId().equals(uuid)) {
                return meal;
            }
        }
        throw new RuntimeException("Resume not found");
    }

    @Override
    public void add(Meal meal) {
        storage.add(meal);
    }

    @Override
    public void remove(String uuid) {
        storage.remove(find(uuid));
    }

    @Override
    public void update(Meal meal) {
        storage.remove(meal);
        add(meal);
    }


    @Override
    public List<Meal> getAll() {
        return storage;
    }

    private Meal find(String uuid) {
        for (Meal meal : storage) {
            if (meal.getId().equals(uuid)) {
                return meal;
            }
        }
        throw new RuntimeException("Resume not found");
    }

}

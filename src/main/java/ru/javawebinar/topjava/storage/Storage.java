package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {

    Meal get(String uuid);

    void add(Meal meal);

    void remove(String uuid);

    void update (Meal meal);

    List<Meal> getAll();
}

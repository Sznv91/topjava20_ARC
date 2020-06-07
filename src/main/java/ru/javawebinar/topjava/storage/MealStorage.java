package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealStorage {

    Meal get(String uuid);

    Meal create(Meal meal);

    void addList(List<Meal> mealList);

    void remove(String uuid);

    Meal update(Meal meal);

    List<Meal> getAll();
}

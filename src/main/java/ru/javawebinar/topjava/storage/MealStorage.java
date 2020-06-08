package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealStorage {

    Meal get(int id);

    Meal create(Meal meal);



    void remove(int id);

    Meal update(Meal meal);

    List<Meal> getAll();

}

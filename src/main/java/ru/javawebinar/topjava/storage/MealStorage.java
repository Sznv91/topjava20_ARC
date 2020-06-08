package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealStorage {

    Meal get(int id);

    Meal create(LocalDateTime dateTime, String description, int calories);



    void remove(int id);

    Meal update(int id, LocalDateTime dateTime, String description, int calories);

    List<Meal> getAll();

}

package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal save(Meal meal) {
        return repository.save(meal);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<MealTo> getAll(int userId, int caloriesPerDay) {
        return MealsUtil.getFilteredTos(repository.getAll(userId), caloriesPerDay, LocalTime.MIN, LocalTime.MAX);
    }

    public void update(Meal meal, int userId) {
        if (meal.getUserID().equals(userId)) {
            checkNotFoundWithId(repository.save(meal), meal.getId());
        } else {
            throw new NotFoundException("meal " + meal.getId() + " not belong userID " + userId);
        }
    }

    public List<MealTo> getFilteredByDate(int authUserId, int caloriesPerDay, LocalDate start, LocalDate end) {
        return MealsUtil.getFilteredTos(repository.getFilteredByDate(authUserId, start, end), caloriesPerDay, LocalTime.MIN, LocalTime.MAX);
    }
}
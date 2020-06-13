package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Service
public class MealService {

    private final MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal save(Meal meal) {
        return meal.getUserID().equals(authUserId()) ? repository.save(meal) : null;
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id, authUserId()), id);
    }

    public Meal get(int id) {
        return repository.get(id, authUserId());
    }

    public List<MealTo> getAll() {
        return MealsUtil.getFilteredTos(repository.getAll(), SecurityUtil.authUserCaloriesPerDay(), LocalTime.MAX, LocalTime.MIN);
    }

    public void update(Meal meal) {
        boolean userPassAuth = repository.get(meal.getId(), authUserId()).getUserID()
                .equals(meal.getUserID());
        if (userPassAuth) {
            checkNotFoundWithId(repository.save(meal), authUserId());
        }

    }

}
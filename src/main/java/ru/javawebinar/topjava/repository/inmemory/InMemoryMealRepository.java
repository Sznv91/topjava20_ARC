package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private Map<Integer, Map<Integer, Meal>> repoByUserId = new ConcurrentHashMap<>(); //k:userId v:Map k:mealId v:Meal
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            try {
                repoByUserId.get(userId).put(meal.getId(), meal);
            } catch (NullPointerException e) {
                Map<Integer, Meal> map = new HashMap<>();
                map.put(meal.getId(), meal);
                repoByUserId.put(userId, map);
            }
            return meal;
        } else {
            if (meal.getUserID().equals(userId)) {
                repoByUserId.get(userId).remove(meal.getId());
                repoByUserId.get(userId).put(meal.getId(), meal);
                return meal;
            }
            return null;
        }

        // handle case: update, but not present in storage
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete id={} userId={} ", id, userId);
        Meal meal = repoByUserId.get(userId).get(id);
        return repoByUserId.get(userId).get(id).getUserID().equals(userId) && repoByUserId.get(userId).remove(meal.getId()) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repoByUserId.get(userId).get(id);
        return meal.getUserID().equals(userId) ? meal : null;

    }

    @Override
    public List<Meal> getAll(int userId) {
        return repoByUserId.get(userId).values()
                .parallelStream().filter(meal -> meal.getUserID().equals(userId))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
    }

    @Override
    public List<Meal> getFilteredByDate(int userId, LocalDate start, LocalDate end) {
        return repoByUserId.get(userId).values().parallelStream().filter(meal -> meal.getDate().compareTo(start) >= 0
                && meal.getDate().compareTo(end) <= 0).collect(Collectors.toList());
    }

}


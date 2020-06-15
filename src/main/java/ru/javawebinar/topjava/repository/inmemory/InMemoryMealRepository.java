package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userID) {
        log.info("delete {} userID " + userID, id);
        Meal meal = repository.get(id);
        return meal.getUserID().equals(userID) && (repository.remove(id) != null);
    }

    @Override
    public Meal get(int id, int userID) {
        Meal meal = repository.get(id);
        return meal.getUserID().equals(userID) ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userID) {
        return repository.values()
                .parallelStream().filter(meal -> meal.getUserID().equals(userID))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getFilteredByDate(int userId, LocalDate start, LocalDate end) {
        return getAll(userId).parallelStream().filter(meal ->
                meal.getDate().compareTo(start) >= 0 &&
                meal.getDate().compareTo(end) <= 0).collect(Collectors.toList());
    }

}


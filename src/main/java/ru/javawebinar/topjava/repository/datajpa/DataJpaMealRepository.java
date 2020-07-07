package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {

    private final CrudMealRepository crudRepository;

    private final DataJpaUserRepository userRepository;

    public DataJpaMealRepository(CrudMealRepository crudRepository, DataJpaUserRepository userRepository) {
        this.crudRepository = crudRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        User user = userRepository.get(userId);
        if (meal.isNew()) {
            meal.setUser(user);
            return crudRepository.save(meal);
        } else {
            Meal existMeal = crudRepository.getByIdAndUserId(meal.getId(), userId);
            if (existMeal != null && existMeal.equals(meal)) {
                meal.setUser(user);
                return crudRepository.save(meal);

            } else {
                return null;
            }
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal existMeal = crudRepository.getByIdAndUserId(id, userId);
        if (existMeal != null && existMeal.getUser().getId().equals(userId)) {
            return crudRepository.deleteById(id) != 0;
        } else {
            return false;
        }

    }

    @Override
    public Meal get(int id, int userId) {
        Meal existMeal = crudRepository.getByIdAndUserId(id, userId);
        if (existMeal != null && existMeal.getUser().getId().equals(userId)) {
            return crudRepository.findById(id).orElse(null);
        } else {
            return null;
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findAllByUserIdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudRepository.findAllByUserIdAndDateTimeGreaterThanEqualAndDateTimeLessThanOrderByDateTimeDesc(userId, startDateTime, endDateTime);
    }
}

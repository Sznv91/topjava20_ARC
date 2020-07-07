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

    private final CrudUserRepository crudUserRepository;

    public DataJpaMealRepository(CrudMealRepository crudRepository, CrudUserRepository userRepository) {
        this.crudRepository = crudRepository;
        this.crudUserRepository = userRepository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        User user = crudUserRepository.getOne(userId);
        if (meal.isNew()) {
            meal.setUser(user);
            return crudRepository.save(meal);
        } else {
            Meal existMeal = crudRepository.findById(meal.getId()).orElse(null);
            if (existMeal != null && existMeal.getUser().id() == userId) {
                meal.setUser(user);
                return crudRepository.save(meal);

            } else {
                return null;
            }
        }
    }

    @Override
    public boolean delete(int id, int userId) {
       return crudRepository.deleteByIdAndUserId(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudRepository.getByIdAndUserId(id, userId);
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

package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User user = em.find(User.class, userId);
        if (meal.isNew()) {
            meal.setUser(user);
            em.persist(meal);
            return meal;
        } else {
            if (em.find(Meal.class, meal.getId()).getUser().equals(user)) {
                meal.setUser(user);
                return em.merge(meal);
            }
        }
        return null;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        User user = em.find(User.class, userId);
        Meal meal = em.find(Meal.class, id);
        if (user != null && meal != null && user.equals(meal.getUser())) {
            return em.createNativeQuery("DELETE FROM meals m WHERE m.id=?").setParameter(1, id).executeUpdate() != 0;
        }
        return false;

    }

    @Override
    public Meal get(int id, int userId) {
        User user = em.find(User.class, userId);
        Meal meal = em.find(Meal.class, id);
        if (user != null && meal != null && user.equals(meal.getUser())) {
            return meal;
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNativeQuery("SELECT * FROM meals WHERE user_id=? ORDER BY date_time DESC", Meal.class).setParameter(1, userId).getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return em.createNativeQuery("SELECT * FROM meals WHERE user_id=?  AND date_time >=  ? AND date_time < ? ORDER BY date_time DESC", Meal.class)
                .setParameter(1, userId)
                .setParameter(2, startDateTime)
                .setParameter(3, endDateTime)
                .getResultList();
    }
}
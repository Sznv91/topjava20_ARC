package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.info("getAll. userId={}", authUserId());
        return service.getAll(authUserId(), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getFilteredByDate(LocalDate start, LocalDate end) {
        log.info("getFilteredByDate userId={} startDate={} endDate={}", authUserId(), start.toString(), end.toString());
        return service.getFilteredByDate(authUserId(), SecurityUtil.authUserCaloriesPerDay(), start, end);
    }

    public Meal get(int id) {
        log.info("get Id={} userId={}", id, authUserId());
        return service.get(id, authUserId());
    }

    public Meal save(Meal meal) {
        log.info("save{}", meal);
        return service.save(meal);
    }

    public void update(Meal meal) {
        log.info("update{}", meal);
        service.update(meal, authUserId());
    }

    public void delete(int id) {
        log.info("delete id={} userId={}", id, authUserId());
        service.delete(id, authUserId());
    }
}
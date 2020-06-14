package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

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
        log.info("getAll. userID={}", authUserId());
        return service.getAll(authUserId(), SecurityUtil.authUserCaloriesPerDay());
    }

    public Meal get(int id) {
        log.info("get ID={} userID={}", id, authUserId());
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
        log.info("delete id={} userID={}", id, authUserId());
        service.delete(id, authUserId());
    }
}
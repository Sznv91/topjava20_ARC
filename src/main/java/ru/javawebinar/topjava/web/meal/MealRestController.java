package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;
import static ru.javawebinar.topjava.util.DateTimeUtil.TIME_FORMATTER;

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

    public List<MealTo> getFilteredByDate(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        log.info("getFilteredByDate userId={} startDate={} startTime={} endDate={} endTime={}",
                authUserId(), startDate.toString(), startTime.format(TIME_FORMATTER),
                endDate.toString(), endTime.format(TIME_FORMATTER));
        return service.getFilteredByDate(authUserId(), SecurityUtil.authUserCaloriesPerDay(), startDate, startTime, endDate, endTime);
    }

    public Meal get(int id) {
        log.info("get Id={} userId={}", id, authUserId());
        return service.get(id, authUserId());
    }

    public Meal create(Meal meal) {
        log.info("save{}", meal);
        ValidationUtil.checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update{}", meal);
        assureIdConsistent(meal, id);
        service.update(meal, id, authUserId());
    }

    public void delete(int id) {
        log.info("delete id={} userId={}", id, authUserId());
        service.delete(id, authUserId());
    }
}
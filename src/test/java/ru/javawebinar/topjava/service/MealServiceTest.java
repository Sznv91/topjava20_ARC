package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal actual = service.get(100002, USER_ID);
        Assert.assertEquals(meal2, actual);
    }

    @Test
    public void getAnotherUser() {
        assertThrows(NotFoundException.class, () -> service.get(100006, USER_ID));
    }

    @Test
    public void delete() {
        assertEquals(meal2, service.get(100002, USER_ID));
        service.delete(100002, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(100002, USER_ID));
    }

    @Test
    public void deleteAnotherUser() {
        assertThrows(NotFoundException.class, () -> service.get(100006, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        assertEquals(MealTestData.getBetweenUserList(), service.getBetweenInclusive(LocalDate.of(2020, 6, 21), LocalDate.of(2020, 6, 21), USER_ID));
    }

    @Test
    public void getAll() {
        assertEquals(getAdminList(), service.getAll(ADMIN_ID));
    }

    @Test
    public void update() {
        assertEquals(meal4, service.get(100004, USER_ID));
        Meal meal = meal4;
        meal.setDescription("UPDATE MEAL");
        service.update(meal, USER_ID);
        assertEquals(meal, service.get(100004, USER_ID));
    }

    @Test
    public void updateAnotherUser() {
        Meal meal = meal4;
        meal.setDescription("UPDATE MEAL");
        assertThrows(NotFoundException.class, () ->service.update(meal, ADMIN_ID));
    }

    @Test
    public void create() {
        assertThrows(NotFoundException.class, () -> service.get(100010, USER_ID));
        Meal expected = new Meal(LocalDateTime.of(2020, 6, 24, 23, 59), "New created meal", 1996);
        assertEquals(expected, service.create(expected, USER_ID));
    }
}
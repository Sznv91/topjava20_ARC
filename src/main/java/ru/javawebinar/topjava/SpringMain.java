package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));

            MealRestController mrc = appCtx.getBean(MealRestController.class);
            mrc.create(new Meal(LocalDateTime.of(2020, 1, 30, 0, 0), "testMeal", 351));
            System.out.println(mrc.getAll());
            System.out.println(mrc.get(1));
            System.out.println(mrc.getFilteredByDate(LocalDate.of(2020, 1, 30), LocalTime.MIN, LocalDate.of(2020, 1, 30), LocalTime.MAX));
            mrc.update(new Meal(1, 1, LocalDateTime.now(), "changed meal", 100), 1);
            System.out.println(mrc.get(1));
        }
    }
}

package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController mealRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = appCtx.getBean(MealRestController.class);
        appCtx.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        switch (request.getParameter("action")) {
            case "create":
                log.info("Meal create");
                mealRestController.create(createMeal(request));
                response.sendRedirect("meals");
                break;
            case "update":
                log.info("Meal update");
                Meal meal = createMeal(request);
                meal.setId(getId(request));
                meal.setUserId(1); //Must set automatically
                mealRestController.update(meal, getId(request));
                response.sendRedirect("meals");
                break;
            case "filter":
                LocalDate startDate = LocalDate.parse(request.getParameter("dateFilterStart"));
                LocalDate endDate = LocalDate.parse(request.getParameter("dateFilterEnd"));
                System.out.println(request.getParameter("timeFilterStart") + " test");
                LocalTime startTime = request.getParameter("timeFilterStart").equals("") ? LocalTime.MIN : LocalTime.parse(request.getParameter("timeFilterStart"));
                LocalTime endTime = request.getParameter("timeFilterEnd").equals("") ? LocalTime.MAX : LocalTime.parse(request.getParameter("timeFilterEnd"));
                request.setAttribute("meals", mealRestController.getFilteredByDate(startDate, startTime, endDate, endTime));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        final Meal meal;

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
                meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "update":
                meal = mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals",
                        mealRestController.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    private Meal createMeal(HttpServletRequest request) {
        LocalDateTime ldt = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        return new Meal(ldt, description, calories);
    }
}

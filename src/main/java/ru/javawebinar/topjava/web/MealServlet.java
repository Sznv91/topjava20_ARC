package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MapMealStorage;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static Logger log;
    private MealStorage mealStorage;
    private DateTimeFormatter dtf;

    @Override
    public void init() throws ServletException {
        log = getLogger(MealServlet.class);
        mealStorage = new MapMealStorage();
        dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        String date = request.getParameter("date");
        LocalDateTime ldt = LocalDateTime.parse(date);
        int calories = Integer.parseInt(request.getParameter("calories"));
        String description = request.getParameter("description");
        String action = request.getParameter("action");
        log.debug(action + " post method. ID: " + id);
        Meal meal = new Meal(id, ldt, description, calories);
        switch (action) {
            case "update":
                mealStorage.update(meal);
                break;
            case "add":
                mealStorage.create(meal);
                break;
        }
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        action = action == null ? "null" : action;
        switch (action) {
            case "delete":
                int id = Integer.parseInt(request.getParameter("id"));
                log.debug("Delete meal ID: " + id + ". Redirect to .meals");
                mealStorage.remove(id);
                response.sendRedirect("meals");
                break;
            case "add":
                LocalDateTime nowWithoutSeconds = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
                Meal meal = new Meal(0, nowWithoutSeconds, "", 0);
                log.debug("Create meal ID: " + meal.getId() + ". Forward to new.jsp");
                request.setAttribute("meal", meal);
                request.setAttribute("action", "add");
                request.getRequestDispatcher("jsp/new.jsp").forward(request, response);
                break;
            case "update":
                id = Integer.parseInt(request.getParameter("id"));
                log.debug("Update meal ID: " + id + ". Forward to new.jsp");
                request.setAttribute("meal", mealStorage.get(id));
                request.setAttribute("action", "update");
                request.getRequestDispatcher("jsp/new.jsp").forward(request, response);
                break;
            default:
                log.debug("forward to meals.jsp");
                String caloriesLimit = request.getParameter("limit");
                int caloriesLimitInt = caloriesLimit == null ? 2000 : Integer.parseInt(caloriesLimit);
                List<MealTo> filteredMeals = MealsUtil.filteredByStreams(mealStorage.getAll(), LocalTime.MIN, LocalTime.MAX, caloriesLimitInt);
                request.setAttribute("storage", filteredMeals);
                request.setAttribute("limit", caloriesLimitInt);
                request.setAttribute("DateTimeFormatter", dtf);
                request.getRequestDispatcher("jsp/meals.jsp").forward(request, response);
                break;
        }
    }
}

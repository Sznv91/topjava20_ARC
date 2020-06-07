package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.ListStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsData;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private Storage storage = new ListStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        try {
            storage.remove(uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String date = request.getParameter("date");
        LocalDateTime ldt = LocalDateTime.parse(date);
        String calories = request.getParameter("calories");
        String description = request.getParameter("description");
        storage.add(new Meal(uuid, ldt, description, Integer.parseInt(calories)));
        response.sendRedirect("./meals?action=view");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals.jsp");
        String action = request.getParameter("action");
        action = action == null ? "null" : action;
        switch (action) {
            case "null":
                List<MealTo> filteredMeals = MealsUtil.filteredByStreams(MealsData.getInstance().meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
                request.setAttribute("meals", MealsUtil.merge(MealsData.getInstance().meals, filteredMeals));
                request.getRequestDispatcher("WEB-INF/jsp/meals.jsp").forward(request, response);
                break;

            case "view":
                request.setAttribute("storage", storage.getAll());
                request.getRequestDispatcher("WEB-INF/jsp/view.jsp").forward(request, response);
                break;
            case "delete":
                String uuid = request.getParameter("id");
                storage.remove(uuid);
                response.sendRedirect("./meals?action=view");
                break;
            case "add":
                Meal meal = new Meal(LocalDateTime.now(), "", 0);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("WEB-INF/jsp/new.jsp").forward(request, response);
                break;
            case "update":
                uuid = request.getParameter("id");
                request.setAttribute("meal", storage.get(uuid));
                request.getRequestDispatcher("WEB-INF/jsp/new.jsp").forward(request, response);
        }

    }
}

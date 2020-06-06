package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsData;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals.jsp");
        List<MealTo> filteredMeals = MealsUtil.filteredByStreams(MealsData.getInstance().meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        request.setAttribute("meals", MealsUtil.merge(MealsData.getInstance().meals, filteredMeals));
        request.getRequestDispatcher("WEB-INF/jsp/meals.jsp").forward(request, response);
    }
}

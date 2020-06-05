package ru.javawebinar.topjava.web;

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

public class MealServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MealsData meals = new MealsData();
        List<MealTo> filteredMeals = MealsUtil.filteredByStreams(meals.getMealsList(), LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        request.setAttribute("meals", MealsUtil.mergeMeals(meals.getMealsList(),filteredMeals));
        request.getRequestDispatcher("WEB-INF/jsp/meals.jsp").forward(request, response);
    }
}

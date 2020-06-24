package ru.javawebinar.topjava.repository.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

import static org.junit.Assert.*;

public class JdbcMealRepositoryTest {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private JdbcMealRepository repository;

    @Before
    public void setUp() throws Exception {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://192.168.88.151:5432/topjava");
        dataSource.setUsername("admin");
        dataSource.setPassword("admin");
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        repository = new JdbcMealRepository(jdbcTemplate, namedJdbcTemplate);
    }

    @Test
    public void save() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void get() {
    }

    @Test
    public void getAll() {
//        System.out.println(repository.getAll(100000));
        List<Meal> expected = MealTestData.getAdminList();
        List<Meal> actual = jdbcTemplate.query("SELECT * FROM meals WHERE user_id=100001 ORDER BY date_time DESC", new Object[]{}, (resultSet, rowNum) ->
                new Meal(resultSet.getInt("id"), resultSet.getTimestamp("date_time").toLocalDateTime(),resultSet.getString("description"), resultSet.getInt("calories")));
        assertEquals(expected,actual);

    }

    @Test
    public void getBetweenHalfOpen() {
    }
}
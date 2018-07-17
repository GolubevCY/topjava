package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.parse("2018-07-18 18:00", FORMATTER), "Ужин", 600);
        Meal created = service.create(newMeal, USER_ID);
        assertMatch(service.getAll(USER_ID), created, USERS_MEAL2, USERS_MEAL1);
    }

    @Test
    public void update() {
        Meal updated = new Meal(ADMINS_MEAL);
        updated.setCalories(800);
        updated.setDescription("Поздний завтрак");
        service.update(updated, ADMIN_ID);
        assertMatch(service.get(ADMINS_MEAL_ID, ADMIN_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void invalidUpdate() {
        service.update(ADMINS_MEAL, USER_ID);
    }

    @Test
    public void get() {
        assertMatch(service.get(USERS_MEAL1_ID, USER_ID), USERS_MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void invalidGet() {
        service.get(ADMINS_MEAL_ID, USER_ID);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), USERS_MEAL2, USERS_MEAL1);
    }

    @Test
    public void delete() {
        service.delete(USERS_MEAL1_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), USERS_MEAL2);
    }

    @Test(expected = NotFoundException.class)
    public void invalidDelete() {
        service.delete(ADMINS_MEAL_ID, USER_ID);
    }

    @Test
    public void getBetweenDates() {
        assertMatch(service.getBetweenDates(
                LocalDate.of(2018, 7, 15),
                LocalDate.of(2018, 7, 16),
                USER_ID), USERS_MEAL1);
    }

    @Test
    public void getBetweenDateTimes() {
        assertMatch(service.getBetweenDateTimes(
                LocalDateTime.parse("2018-07-17 15:00", FORMATTER),
                LocalDateTime.parse("2018-07-17 16:00", FORMATTER),
                USER_ID), USERS_MEAL2);
    }
}
package ru.javawebinar.topjava.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.meal.MealRestController;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration("classpath:spring/spring-app-inMemoryTest.xml")
@RunWith(SpringRunner.class)
public class InMemoryMealRestControllerSpringTest {

    @Autowired
    private MealRestController controller;

    @Autowired
    private InMemoryMealRepositoryImpl repository;


    @Test
    public void testDelete() throws Exception {
        controller.delete(USERS_MEAL1.getId());
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        controller.delete(10);
    }
}
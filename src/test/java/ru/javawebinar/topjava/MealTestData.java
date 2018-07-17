package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

abstract public class MealTestData {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static final int USERS_MEAL1_ID = START_SEQ + 2;
    public static final int USERS_MEAL2_ID = START_SEQ + 3;
    public static final int ADMINS_MEAL_ID = START_SEQ + 4;

    public static final Meal USERS_MEAL1 = new Meal(USERS_MEAL1_ID, LocalDateTime.parse("2018-07-16 08:06", FORMATTER), "Ужин", 600);
    public static final Meal USERS_MEAL2 = new Meal(USERS_MEAL2_ID, LocalDateTime.parse("2018-07-17 15:06", FORMATTER), "Обед", 1100);
    public static final Meal ADMINS_MEAL = new Meal(ADMINS_MEAL_ID, LocalDateTime.parse("2018-07-17 09:00", FORMATTER), "Завтрак", 1000);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField (expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}

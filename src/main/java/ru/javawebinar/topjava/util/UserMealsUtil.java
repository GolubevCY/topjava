package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).forEach(System.out::println);
        getFilteredWithExceededStream(mealList, LocalTime.of(20, 7), LocalTime.of(20, 0), 2000).forEach(System.out::println);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesPerDayMap = new HashMap<>();
        for (UserMeal meal : mealList) {
            caloriesPerDayMap.merge(getLocalDate(meal), meal.getCalories(), (oldVal, newVal) -> oldVal + newVal);
        }

        List<UserMealWithExceed> result = new ArrayList<>();
        for (UserMeal meal : mealList) {
            if (isValidTime(meal, startTime, endTime)) {
                result.add(getUserMealWithExceed(meal, caloriesPerDayMap, caloriesPerDay));
            }
        }

        return result;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesPerDayMap = mealList.stream().collect(Collectors.toMap(UserMealsUtil::getLocalDate, UserMeal::getCalories, (oldVal, newVal) -> oldVal + newVal));
        return mealList.stream()
                .filter(meal -> isValidTime(meal, startTime, endTime))
                .map(meal -> getUserMealWithExceed(meal, caloriesPerDayMap, caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static LocalDate getLocalDate(UserMeal meal) {
        return meal.getDateTime().toLocalDate();
    }

    private static boolean isValidTime(UserMeal meal, LocalTime startTime, LocalTime endTime) {
        LocalTime time = meal.getDateTime().toLocalTime();
        return (time.isAfter(startTime) || time.equals(startTime)) && (time.isBefore(endTime) || time.equals(endTime));
    }

    private static UserMealWithExceed getUserMealWithExceed(UserMeal meal, Map<LocalDate, Integer> caloriesPerDayMap, int caloriesPerDay) {
        return new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), caloriesPerDayMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay);
    }

}

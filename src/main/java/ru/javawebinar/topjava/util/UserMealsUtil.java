package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),

                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),

                new UserMeal(LocalDateTime.of(2022, Month.OCTOBER, 27, 11, 30), "Breakfast", 700),
                new UserMeal(LocalDateTime.of(2022, Month.OCTOBER, 27, 17, 30), "Ужин", 900),

                new UserMeal(LocalDateTime.of(2022, Month.NOVEMBER,2,12, 0), "Lunch", 700)
        );


        // List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(12, 0), LocalTime.of(22, 0), 400);
        //mealsTo.forEach(System.out::println);

        // List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        //  mealsTo.forEach(System.out::println);

        //System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 900));
        System.out.println(filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(10, 0), 1500));

    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        return null;
    }
    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Boolean> dateToExcessMap = meals.stream()
                .collect(Collectors.groupingBy(UserMeal::getLocalDate))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().stream().mapToInt(UserMeal::getCalories).sum() > caloriesPerDay));

        return meals.stream().map(userMeal ->
                        new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(),
                                userMeal.getCalories(), dateToExcessMap.get(userMeal.getLocalDate())))
                .filter(userMealWithExcess -> {
                    LocalTime localTime = userMealWithExcess.getDateTime().toLocalTime();
                    return !localTime.isAfter(endTime) && !localTime.isBefore(startTime);
                }).collect(Collectors.toList());
    }
}


package ru.javawebinar.topjava.web;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);

    private static final InMemoryMealRepository mealRepository = new InMemoryMealRepository();

    private static final String DEFAULT_ENCODING = "UTF-8";

    private static final String LIST_MEAL = "listMeal.jsp";

    private static final String UNDEFINED = "undefined.jsp";

    private static final String INSERT_OR_EDIT = "meal.jsp";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");


    @Override
    public void init(ServletConfig config) {
        MealsUtil.getMeals().forEach(meal -> mealRepository.add(meal));
        log.debug("Add default values to the InMemoryMealRepository");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding(DEFAULT_ENCODING);

        String forward;
        String action = request.getParameter("action");

        if ("listMeal".equalsIgnoreCase(action)) {
            forward = LIST_MEAL;
            request.setAttribute("meals", getAllMeals());
            log.debug("redirect to {}", LIST_MEAL);
        } else if ("delete".equalsIgnoreCase(action)) {
            forward = LIST_MEAL;
            mealRepository.delete(getMealId(request));
            request.setAttribute("meals", getAllMeals());
            log.debug("redirect to {}", LIST_MEAL);
        } else if ("edit".equalsIgnoreCase(action)) {
            forward = INSERT_OR_EDIT;
            Meal meal = mealRepository.getById(getMealId(request));
            request.setAttribute("meal", meal);
            log.debug("redirect to {}", INSERT_OR_EDIT);
        } else if ("insert".equalsIgnoreCase(action)) {
            forward = INSERT_OR_EDIT;
            request.setAttribute("meal", new Meal());
            log.debug("redirect to {}", INSERT_OR_EDIT);
        } else {
            forward = UNDEFINED;
            log.debug("redirect to {}", UNDEFINED);
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(DEFAULT_ENCODING);
        Meal meal = new Meal();
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        meal.setDescription(request.getParameter("description"));
        meal.setDateTime(getDateTime(request));

        String mealId = request.getParameter("mealId");
        if (StringUtils.isBlank(mealId)) {
            mealRepository.add(meal);
            log.debug("add meal: {}", meal.toString());
        } else {
            meal.setId(Long.parseLong(mealId));
            mealRepository.update(meal);
            log.debug("update meal: {}", meal.toString());
        }

        request.setAttribute("meals", getAllMeals());
        request.getRequestDispatcher(LIST_MEAL).forward(request, response);
        log.debug("redirect to {}", LIST_MEAL);
    }

    private List<MealWithExceed> getAllMeals() {
        return MealsUtil.convertToMealWithExceed(mealRepository.getAll(), MealsUtil.getCaloriesPerDay());
    }

    private Long getMealId(HttpServletRequest request) {
        return Long.parseLong(request.getParameter("mealId"));
    }

    private LocalDateTime getDateTime(HttpServletRequest request) {
        String str = request.getParameter("dateTime");

        if (StringUtils.isNoneBlank(str)) {
            try {
                return LocalDateTime.parse(str, formatter);
            } catch (DateTimeParseException ex) {
                log.error("Invalid dateTime value", ex);
            }
        }
        return LocalDateTime.now();
    }
}
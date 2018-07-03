package ru.javawebinar.topjava.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import ru.javawebinar.topjava.web.MealServlet;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class DateTimeUtils {

    private static final Logger log = getLogger(DateTimeUtils.class);

    private DateTimeUtils() {
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
        if (localDateTime != null) {
            try {
                return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
            } catch (DateTimeException ex) {
                log.error("Error during conversion LocalDateTime to String", ex);
            }
        }
        return "";
    }
}
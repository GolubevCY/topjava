package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UserUtil {
    public static final List<User> USERS = Arrays.asList(
            new User(null, "admin1", "admin1@mail.ru", "password", Role.ROLE_USER),
            new User(null, "admin2", "admin2@mail.ru", "password", Role.ROLE_ADMIN, Role.ROLE_USER),
            new User(null, "user1", "user1@mail.ru", "password", Role.ROLE_USER),
            new User(null, "user2", "user2@mail.ru", "password", Role.ROLE_USER)
    );
}

package db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.User;

public class UserDatabase {
    private static final Logger log = LoggerFactory.getLogger(UserDatabase.class);
    private static Map<String, User> users = new HashMap<>();

    public static Optional<User> addUser(User user) {
        if (users.containsKey(user.getUserId())) {
            return Optional.empty();
        }
        users.put(user.getUserId(), user);
        log.debug("user: {}", user);
        return Optional.ofNullable(user);
    }

    public static Optional<User> findUserById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}

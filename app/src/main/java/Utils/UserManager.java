package Utils;

import com.example.groupe_39.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.Requester;
import entities.User;

public class UserManager {
    private static UserManager instance; // Singleton instance
    private HashMap<String, User> userMap = new HashMap<>();

    // Private constructor to prevent instantiation
    private UserManager() {}

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void addUser(User user) {
        userMap.put(user.getEmail(), user);
    }

    public User authenticate(String email, String password) {
        User user = userMap.get(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    public boolean removeRequesters(List<Requester> requesters) {
        boolean removedAny = false;

        for (Requester requester : requesters) {
            if (userMap.containsKey(requester.getEmail())) {
                userMap.remove(requester.getEmail());
                removedAny = true;
            }
        }
        return removedAny;
    }

}


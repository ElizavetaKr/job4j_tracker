package ru.job4j.ex.user;

import ru.job4j.ex.user.User;
import ru.job4j.ex.user.UserInvalidException;
import ru.job4j.ex.user.UserNotFoundException;

public class UserStore {
    public static User findUser(User[] users, String login) throws UserNotFoundException {
        User result = null;
        for (User user : users) {
            if (user.getUsername().equals(login)) {
                result = user;
                break;
            }
        }
        if (result == null) {
            throw new UserNotFoundException("User not found");
        }
        return result;
    }

    public static boolean validate(User user) throws UserInvalidException {
        if (!user.isValid() || user.getUsername().length() < 3) {
            throw new UserInvalidException("User invalid");
        }
        return true;
    }

    public static void main(String[] args) {
        User[] users = {
                new User("Petr Arsentev", true)
        };
        try {
            User user = findUser(users, "Petr Arsentev");
            if (user != null) {
                if (validate(user)) {
                    System.out.println("This user has an access");
                }
            }

        } catch (UserInvalidException uie) {
            uie.printStackTrace();
        } catch (UserNotFoundException ufe) {
            ufe.printStackTrace();
        }
    }
}

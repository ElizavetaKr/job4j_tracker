package ru.job4j.ex;

public class UserStore {
    public static User findUser(User[] users, String login) throws UserNotFoundException {
        User user = null;
        for (int i = 0; i < users.length; i++) {
            if (users[i].getUsername().equals(login)) {
                user = users[i];
                break;
            }
        }
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return user;
    }

    public static boolean validate(User user) throws UserInvalidException {
        boolean result = false;
        if (user.getUsername().length() >= 3) {
            result = user.isValid();
        }
        if (!result) {
            throw new UserInvalidException("User invalid");
        }
        return result;
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

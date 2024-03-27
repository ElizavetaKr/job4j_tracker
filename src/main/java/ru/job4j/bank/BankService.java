package ru.job4j.bank;

import ru.job4j.bank.Account;
import ru.job4j.bank.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс описывает модель банковской системы
 *
 * @author Елизавета Крюкова
 * @version 1.0
 */
public class BankService {
    /**
     * Хранение всех пользователей системы с привязанными к ним счетами
     * осуществляется в коллекции типа HashMap
     */
    private final Map<User, List<Account>> users;

    public BankService(Map<User, List<Account>> users) {
        this.users = users;
    }

    /**
     * Метод принимает на вход пользователя и добавляет его в систему.
     * Если пользователь уже есть в системе, то метод нового пользователя не добавляет
     *
     * @param user пользователь, которого необходимо зарегистрировать
     */
    public void addUser(User user) {
        users.putIfAbsent(user, new ArrayList<Account>());
    }

    /**
     * Метод удаляет пользователя из системы.
     *
     * @param passport паспорт пользователя
     */
    public void deleteUser(String passport) {
        users.remove(new User(passport, ""));
    }

    /**
     * Метод добавляет новый банковский счет к пользователю.
     * У пользователя системы могут быть несколько счетов.
     *
     * @param passport паспорт пользователя
     * @param account  банковский счет
     */
    public void addAccount(String passport, Account account) {
        User user = findByPassport(passport);
        if (user != null) {
            List<Account> accounts = users.get(user);
            if (!accounts.contains(account)) {
                accounts.add(account);
            }
        }
    }

    /**
     * Метод позволяет найти пользователя по номеру паспорта.
     *
     * @param passport паспорт пользователя
     * @return возвращает пользователя или null если пользователь не найден
     */
    public User findByPassport(String passport) {
        return users.keySet()
                .stream()
                .filter(user -> user.getPassport().equals(passport))
                .findFirst()
                .orElse(null);
    }

    /**
     * Метод позволяет найти счет пользователя по номеру паспорта и реквизитам.
     *
     * @param passport  паспорт пользователя
     * @param requisite реквизиты счета пользователя
     * @return возвращает счет пользователя или null если счет пользователя не найден
     */
    public Account findByRequisite(String passport, String requisite) {
        User key = findByPassport(passport);
        if (key == null) {
            return null;
        }
        return users.get(key)
                .stream()
                .filter(account -> account.getRequisite().equals(requisite))
                .findFirst()
                .orElse(null);
    }

    /**
     * Метод позволяет переводить деньги с одного банковского счета на другой.
     * Если счёт не найден или не хватает денег на счёте, с которого переводят,
     * то метод возвращает false.
     *
     * @param sourcePassport       паспорт пользователя, со счета которого переводят деньги
     * @param sourceRequisite      реквизиты счета пользователя, с которого переводят деньги
     * @param destinationPassport  паспорт пользователя, на счет которого переводят деньги
     * @param destinationRequisite реквизиты счета пользователя, на который переводят деньги
     * @param amount               сумма перевода
     */
    public boolean transferMoney(String sourcePassport, String sourceRequisite,
                                 String destinationPassport, String destinationRequisite,
                                 double amount) {
        boolean result = false;
        Account sourceAccount = findByRequisite(sourcePassport, sourceRequisite);
        Account destinationAccount = findByRequisite(destinationPassport, destinationRequisite);
        if (sourceAccount != null && destinationAccount != null && sourceAccount.getBalance() >= amount) {
            sourceAccount.setBalance(sourceAccount.getBalance() - amount);
            destinationAccount.setBalance(destinationAccount.getBalance() + amount);
            result = true;
        }
        return result;
    }

    /**
     * Метод позволяет получить список всех банковских счетов пользователя
     *
     * @param user пользователь, чьи счета нам необходимо получить
     * @return возвращает список всех банковских счетов пользователя в виде коллекции
     */
    public List<Account> getAccounts(User user) {
        return users.get(user);
    }
}
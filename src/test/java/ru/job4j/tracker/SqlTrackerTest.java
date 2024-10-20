package ru.job4j.tracker;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.tracker.Item;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

public class SqlTrackerTest {

    private static Connection connection;

    @BeforeAll
    public static void initConnection() {
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("db/liquibase_test.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")

            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @AfterAll
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @AfterEach
    public void wipeTable() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("delete from items")) {
            statement.execute();
        }
    }

    @Test
    public void whenSaveItemAndFindByGeneratedIdThenMustBeTheSame() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        assertThat(tracker.findById(item.getId())).isEqualTo(item);
    }

    @Test
    public void whenReplaceExistItemThenTrue() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item1 = new Item("item1");
        Item item2 = new Item("item2");
        tracker.add(item1);
        tracker.add(item2);
        assertThat(tracker.replace(item1.getId(), item2)).isTrue();
    }

    @Test
    public void whenReplaceNotExistItemThenFalse() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        assertThat(tracker.replace(1, item)).isFalse();
    }

    @Test
    public void whenDeleteItemThenMustNotExist() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        tracker.delete(item.getId());
        assertThat(tracker.findById(item.getId())).isNull();
    }

    @Test
    public void whenFindByIdNotExistIdThenNull() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        assertThat(tracker.findById(2)).isNull();
    }

    @Test
    public void whenFindByNameThenAllItemsHaveTheSameName() {
        SqlTracker tracker = new SqlTracker(connection);
        String expectedName = "item";
        tracker.add(new Item("item"));
        tracker.add(new Item("item"));
        tracker.add(new Item("item"));
        tracker.add(new Item("item"));
        tracker.add(new Item("item2"));
        List<Item> items = tracker.findByName(expectedName);
        assertThat(items).isNotEmpty()
                .hasSize(4)
                .allSatisfy(i -> assertThat(i.getName()).isEqualTo(expectedName));
    }

    @Test
    public void whenFindByNotExistNameThenEmptyList() {
        SqlTracker tracker = new SqlTracker(connection);
        tracker.add(new Item("item1"));
        tracker.add(new Item("item2"));
        tracker.add(new Item("item3"));
        List<Item> items = tracker.findByName("item");
        assertThat(items).isEmpty();
    }

    @Test
    public void whenFindAll() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item1 = new Item("item1");
        Item item2 = new Item("item2");
        Item item3 = new Item("item3");
        List<Item> expected = List.of(item1, item2, item3);
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        List<Item> items = tracker.findAll();
        assertThat(items).isNotEmpty()
                .hasSize(3);
        assertThat(items).isEqualTo(expected);
    }

    @Test
    public void whenFindAllForEmptyThenEmptyList() {
        SqlTracker tracker = new SqlTracker(connection);
        List<Item> items = tracker.findAll();
        assertThat(items).isEmpty();
    }

}
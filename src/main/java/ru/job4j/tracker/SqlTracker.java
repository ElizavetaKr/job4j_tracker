package ru.job4j.tracker;

import ru.job4j.tracker.Item;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SqlTracker implements Store {

    private Connection connection;

    public SqlTracker() {
        init();
    }

    public SqlTracker(Connection connection) {
        this.connection = connection;
    }

    private void init() {
        try (InputStream input = SqlTracker.class.getClassLoader()
                .getResourceAsStream("db/liquibase.properties")) {
            Properties config = new Properties();
            config.load(input);
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

    @Override
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Override
    public Item add(Item item) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO items(name, created) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            Timestamp timestampFromLDT = Timestamp.valueOf(item.getCreated());
            statement.setTimestamp(2, timestampFromLDT);
            statement.execute();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return item;
    }

    @Override
    public boolean replace(int id, Item item) {
        if (findById(id) == null) {
            return false;
        }
        try (Statement statement = connection.createStatement()) {
            Timestamp timestampFromLDT = Timestamp.valueOf(item.getCreated());
            statement.execute(String.format("UPDATE items SET name = '%s', created = '%s'"
                            + "WHERE id = %s;",
                    item.getName(), timestampFromLDT, id));
            item.setId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public void delete(int id) {
        Item item = findById(id);
        if (item != null) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(String.format("DELETE FROM items "
                        + "WHERE id = %s;", id));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        try (var statement = connection.createStatement()) {
            var selection = statement.executeQuery(String.format(
                    "SELECT * FROM items"));
            while (selection.next()) {
                items.add(create(selection.getInt("id"),
                        selection.getString("name"),
                        selection.getTimestamp("created")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return items;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> items = new ArrayList<>();
        try (var statement = connection.createStatement()) {
            var selection = statement.executeQuery(String.format(
                    "SELECT * FROM items WHERE name = '%s'", key));
            while (selection.next()) {
                items.add(create(selection.getInt("id"),
                        selection.getString("name"),
                        selection.getTimestamp("created")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return items;
    }

    @Override
    public Item findById(int id) {
        Item item = null;
        try (var statement = connection.createStatement()) {
            var selection = statement.executeQuery(String.format(
                    "SELECT * FROM items WHERE id = %s", id));
            if (selection.next()) {
                item = create(selection.getInt("id"),
                        selection.getString("name"),
                        selection.getTimestamp("created"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return item;
    }

    private Item create(int id, String name, Timestamp created) {
        LocalDateTime localDateTime = created.toLocalDateTime();
        return new Item(id, name, localDateTime);
    }
}
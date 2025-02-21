package ru.job4j.tracker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class HbmTracker implements Store, AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public Item add(Item item) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return item;
    }

    @Override
    public boolean replace(int id, Item item) {
        if (findById(id) == null) {
            return false;
        }
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("UPDATE Item SET name = :fName WHERE id = :fId")
                    .setParameter("fName", item.getName())
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public void delete(int id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "DELETE Item WHERE id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Item> findAll() {
        List<Item> result;
        Session session = sf.openSession();
        Query query = session.createQuery("from Item");
        result = query.list();
        session.close();
        return result;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> result;
        Session session = sf.openSession();
        Query query = session.createQuery("from Item WHERE name = :fName");
        query.setParameter("fName", key);
        result = query.list();
        session.close();
        return result;
    }

    @Override
    public Item findById(int id) {
        Item result;
        Session session = sf.openSession();
        Query query = session.createQuery("from Item WHERE id = :fId");
        query.setParameter("fId", id);
        result = (Item) query.uniqueResult();
        session.close();
        return result;
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
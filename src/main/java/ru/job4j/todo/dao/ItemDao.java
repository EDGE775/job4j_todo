package ru.job4j.todo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Item;

import java.util.List;

public class ItemDao implements AutoCloseable {

    private final StandardServiceRegistry registry;

    private final SessionFactory sf;

    public ItemDao() {
        this.registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        this.sf = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }

    public Item add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    public List<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> result = session.createQuery("from Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public Item findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item result = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public boolean replace(int id, Item item) {
        boolean result = true;
        try {
            Session session = sf.openSession();
            session.beginTransaction();
            session.update(item);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}

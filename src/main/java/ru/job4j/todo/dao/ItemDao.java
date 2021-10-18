package ru.job4j.todo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.todo.model.Item;

import java.util.List;
import java.util.function.Function;

public class ItemDao implements AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(ItemDao.class.getName());

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
        return this.tx(
                session -> {
                    session.save(item);
                    return item;
                }

        );
    }

    public List<Item> findAll() {
        return this.tx(
                session -> session.createQuery("from Item").list()
        );
    }

    public Item findById(int id) {
        return this.tx(
                session -> session.get(Item.class, id)
        );
    }

    public boolean replace(int id, Item item) {
        return this.tx(
                session -> {
                    boolean result = true;
                    Query query = session.createQuery(
                            "update Item set "
                                    + "description = :description, "
                                    + "created = :created, "
                                    + "done = :done "
                                    + "where id = :id");
                    query.setParameter("description", item.getDescription());
                    query.setParameter("created", item.getCreated());
                    query.setParameter("done", item.isDone());
                    query.setParameter("id", id);
                    if (query.executeUpdate() > 0) {
                        result = false;
                    }
                    return result;
                }
        );
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            LOG.error("Ошибка при работе с базой данных.", e);
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}

package ru.job4j.todo.service;

import ru.job4j.todo.dao.ItemDao;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HbmService implements AutoCloseable {

    private final ItemDao itemDao;

    private HbmService() {
        this.itemDao = new ItemDao();
    }

    public Item addItem(Item item) {
        return itemDao.addItem(item);
    }

    public boolean updateItem(Item item) {
        return itemDao.replaceItem(item);
    }

    public List<Item> findAllItems() {
        return itemDao.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Item::getId))
                .collect(Collectors.toList());
    }

    public Item findItemById(int id) {
        return itemDao.findById(id);
    }

    public void saveUser(User user) {
        if (user.getId() == 0) {
            itemDao.createUser(user);
        } else {
            itemDao.updateUser(user);
        }

    }

    public User findUserByEmail(String email) {
        return itemDao.findUserByEmail(email);
    }

    @Override
    public void close() throws Exception {
        itemDao.close();
    }

    public Category findCategoryById(int id) {
        return itemDao.findCategoryById(id);
    }

    public List<Category> findAllCategories() {
        return itemDao.findAllCategories()
                .stream()
                .sorted(Comparator.comparingInt(Category::getId))
                .collect(Collectors.toList());
    }

    private static final class Lazy {
        private static final HbmService INST = new HbmService();
    }

    public static HbmService instOf() {
        return Lazy.INST;
    }

}

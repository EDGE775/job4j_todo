package ru.job4j.todo.service;

import ru.job4j.todo.dao.ItemDao;
import ru.job4j.todo.model.Item;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HbmService implements AutoCloseable {

    private final ItemDao itemDao;

    private HbmService() {
        this.itemDao = new ItemDao();
    }

    public Item addItem(Item item) {
        return itemDao.add(item);
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

    public boolean updateItem(Item item) {
        return itemDao.replace(item.getId(), item);
    }

    @Override
    public void close() throws Exception {
        itemDao.close();
    }

    private static final class Lazy {
        private static final HbmService INST = new HbmService();
    }

    public static HbmService instOf() {
        return Lazy.INST;
    }

}
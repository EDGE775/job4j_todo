package ru.job4j.todo.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.service.HbmService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class IndexServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(HbmService.instOf().findAllItems());
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Item item = GSON.fromJson(req.getReader(), Item.class);
        HbmService.instOf().addItem(item);
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(item);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Item tempItem = GSON.fromJson(req.getReader(), Item.class);
        int id = tempItem.getId();
        boolean done = tempItem.isDone();
        Item item = HbmService.instOf().findItemById(id);
        if (item != null) {
            item.setDone(done);
            HbmService.instOf().updateItem(item);
            OutputStream output = resp.getOutputStream();
            String json = GSON.toJson(item);
            output.write(json.getBytes(StandardCharsets.UTF_8));
            output.flush();
            output.close();
        }
    }
}

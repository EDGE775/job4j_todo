package ru.job4j.todo.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.todo.dao.ItemDao;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.HbmService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class UserServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    private static final Logger LOG = LoggerFactory.getLogger(ItemDao.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        HttpSession session = req.getSession();
        String json = GSON.toJson((User) session.getAttribute("user"));
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }
}

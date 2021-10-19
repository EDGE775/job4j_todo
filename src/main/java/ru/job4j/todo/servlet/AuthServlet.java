package ru.job4j.todo.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.HbmService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Objects;

public class AuthServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("auth.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        HbmService service = HbmService.instOf();
        User user = service.findUserByEmail(email);
        if (user == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Пользователь с такой почтой не найден!");
        } else {
            if (Objects.equals(password, user.getPassword())) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                resp.sendRedirect(req.getContextPath() + "/index.do");
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный пароль!");
            }
        }
    }
}
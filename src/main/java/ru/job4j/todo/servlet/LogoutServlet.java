package ru.job4j.todo.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.todo.dao.ItemDao;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(ItemDao.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        resp.sendRedirect(req.getContextPath() + "/auth.do");
    }
}

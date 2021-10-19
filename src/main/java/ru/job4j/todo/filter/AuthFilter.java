package ru.job4j.todo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.todo.dao.ItemDao;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(ItemDao.class.getName());

    @Override
    public void doFilter(ServletRequest sreq, ServletResponse sresp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) sreq;
        HttpServletResponse resp = (HttpServletResponse) sresp;
        String uri = req.getRequestURI();
        if (uri.endsWith("auth.do")
                || uri.endsWith("reg.do")) {
            chain.doFilter(sreq, sresp);
            return;
        }
        HttpSession session = req.getSession();
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth.html");
            return;
        }
        chain.doFilter(sreq, sresp);
    }
}
package com.revature.servlet;

import com.revature.controller.SessionController;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/session",loadOnStartup = 1)
public class SessionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("session");
//        RequestDispatcher view = req.getRequestDispatcher("session/login.html");
//        view.forward(req, resp);
//        req.getRequestDispatcher("welcome.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("session post");
        new SessionController().createSession(req, resp);
    }

    @Override
    public void init() throws ServletException {
    }

    @Override
    public void destroy() {
    }
}

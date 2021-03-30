package com.revature.servlet;

import com.revature.controller.FrontController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class CustomServlet extends HttpServlet {
    FrontController front = FrontController.getInstance();

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        front.dispatchRequest(this.getClass(), httpMethod.GET , req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        front.dispatchRequest(this.getClass(), httpMethod.POST , req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        front.dispatchRequest(this.getClass(), httpMethod.PUT , req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        front.dispatchRequest(this.getClass(), httpMethod.DELETE , req, resp);
    }
}
package com.revature.gopo.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/")
public class HomeServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.println("<html><body><h1>Group 1.1 - Project 1.5</h1>"+
                "<h2>Index</h2>"+
                "<div><a href='/user'>user</a></div>"+
                "<div><a href='/reimbursement'>reimbursement</a></div></body></html>");
    }
}

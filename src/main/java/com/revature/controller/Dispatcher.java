package com.revature.controller;

import com.revature.dao.*;
import com.revature.model.*;
import com.revature.servlet.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

//https://www.tutorialspoint.com/design_pattern/front_controller_pattern.htm
public class Dispatcher {
    private ReimbursementDao reimbursementView;
    private UserDao userView;

    public Dispatcher(){
        reimbursementView = new ReimbursementDao();
        userView = new UserDao();
    }

    public void dispatch(Class<? extends HttpServlet> clazz, httpMethod method, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        if(clazz.equals(UserServlet.class)){
            // do user thing
            switch(method){
                case GET:
                    if (req.getParameter("get-all") != null && req.getParameter("get-all").equals("true")){
                        out.println("Users");
                        for(User u : userView.getList()){
                            out.println(u.toString());
                        }
                    } else {

                    }
                    break;
                case PUT:
                    break;
                case POST:
                    break;
                case DELETE:
                    break;
                default:
                    RuntimeException e = new RuntimeException("You shouldn't have come here");
                    e.printStackTrace();
                    throw e;
            }
        } else{
            // do something else
        }
    }

    /**
     *
     * @param req HttpServletRequest
     * @param param parameter name
     * @param expected value to check <code>param</code> against
     * @return true if <code>param</code> and <code>expected</code> match, false otherwise
     * @throws ServletException if the parameter doesn't exist
     */
    private boolean checkParameter(HttpServletRequest req, String param, String expected) throws ServletException{
        if (req.getParameter(param) == null){
            throw new ServletException("Parameter doesn't exist", new NullPointerException());
        } else {
            return req.getParameter(param).equals(expected);
        }
    }
}
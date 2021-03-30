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

    @SuppressWarnings("rawtypes")
    public void dispatch(Class<? extends HttpServlet> clazz, httpMethod method, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        GenericDao dao;
        if(clazz.equals(UserServlet.class)){
            dao = userView;
            out.println("Users");
        } else if(clazz.equals(ReimbursementServlet.class)){
            dao = reimbursementView;
            out.println("Reimbursement");
        } else{
            // if the class doesn't match a dao
            RuntimeException e = new RuntimeException("no DAO assigned for this class");
            e.printStackTrace();
            throw e;
        }
        switch(method){
            case GET:
                if (req.getParameter("id") != null){
                    out.println(dao.getById(Integer.parseInt(req.getParameter("id"))).toString());
                } else if (req.getParameter("username") != null){
                    out.println(dao.getByUsername(req.getParameter("username")).toString());
                } else if (req.getParameter("user_id") != null){
                    for(Object o : dao.getByUserId(Integer.parseInt(req.getParameter("user_id")))){
                        out.println(o.toString());
                    }
                } else {
                    // get all
                    for(Object o : dao.getList()){
                        out.println(o.toString());
                    }
                }
                break;
            case PUT:
                break;
            case POST:
                break;
            case DELETE:
                break;
            default:
                RuntimeException e = new RuntimeException("You shouldn't have come here!");
                e.printStackTrace();
                throw e;
        }
    }

    /**
     * @param req HttpServletRequest
     * @param param parameter name
     * @param expected value to check <code>param</code> against
     * @return true if <code>param</code> and <code>expected</code> match, false otherwise or if the parameter doesn't exist
     */
    private boolean checkParameter(HttpServletRequest req, String param, String expected){
        if (req.getParameter(param) == null){
            return false;
        } else {
            return req.getParameter(param).equals(expected);
        }
    }

    /**
     * @param req HttpServletRequest
     * @param param parameter name
     * @param expected value to check <code>param</code> against
     * @return true if <code>param</code> and <code>expected</code> match, false otherwise
     * @throws ServletException if the parameter doesn't exist
     */
    private boolean checkParameterThrowIfNull(HttpServletRequest req, String param, String expected) throws ServletException{
        if (req.getParameter(param) == null){
            throw new ServletException("Parameter doesn't exist", new NullPointerException());
        } else {
            return req.getParameter(param).equals(expected);
        }
    }
}
package com.revature.gopo.controller;

import com.revature.gopo.service.GenericService;
import com.revature.gopo.service.ReimbursementService;
import com.revature.gopo.service.UserService;
import com.revature.gopo.servlet.ReimbursementServlet;
import com.revature.gopo.servlet.UserServlet;
import com.google.gson.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

//https://www.tutorialspoint.com/design_pattern/front_controller_pattern.htm
public class Dispatcher {
    private final ReimbursementService reimbursementView;
    private final UserService userView;

    //TODO look at documentation
    Gson gson;

    public Dispatcher(){
        reimbursementView = new ReimbursementService();
        userView = new UserService();
        gson = new Gson();
    }

    /**
     *
     * @param clazz the calling Class, to differentiate UserServlet, ReimbersmentServlet, etc.
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    public void dispatch(Class<? extends HttpServlet> clazz, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        GenericService service;

        // Map parameters to their first value
        HashMap<String,String> parameterMap = new HashMap<>();
        for(Map.Entry<String,String[]> m :req.getParameterMap().entrySet()){
            parameterMap.put(m.getKey(),m.getValue()[0]);
        }
        if(clazz.equals(UserServlet.class)){
            service = userView;
            out.println("Users");
        } else if(clazz.equals(ReimbursementServlet.class)){
            service = reimbursementView;
            out.println("Reimbursement");
        } else{
            // if the class doesn't match a service
            RuntimeException e = new RuntimeException("no DAO assigned for this class");
            e.printStackTrace();
            throw e;
        }
        switch(req.getMethod()){
            case "GET":
                if (parameterMap.get("id") != null){
                    gson.toJson(service.getById(Integer.parseInt(parameterMap.get("id"))),out);
                } else if (parameterMap.get("user_id") != null){
                    for(Object o : service.getByUserId(Integer.parseInt(parameterMap.get("user_id")))){
                        gson.toJson(o,out);
                    }
                } else {
                    // get all
                    if(service.getList() != null){
                        for(Object o : service.getList()){
                            gson.toJson(o,out);
                        }
                    }
                }
                break;
            case "PUT":
                service.createOrUpdate(parameterMap);
                out.println("PUT");
                break;
            case "POST":
                service.create(parameterMap);
                out.println("POSTED");
                break;
            case "DELETE":
                service.delete(parameterMap);
                out.println("DELETED");
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
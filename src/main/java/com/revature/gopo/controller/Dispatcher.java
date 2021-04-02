package com.revature.gopo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.gopo.model.Reimbursement;
import com.revature.gopo.model.User;
import com.revature.gopo.service.GenericService;
import com.revature.gopo.service.ReimbursementService;
import com.revature.gopo.service.UserService;
import com.revature.gopo.servlet.ReimbursementServlet;
import com.revature.gopo.servlet.UserServlet;
import com.google.gson.*;
import org.apache.log4j.Logger;

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
    private static final Logger LOGGER = Logger.getLogger(Dispatcher.class);
    private final ReimbursementService reimbursementView;
    private final UserService userView;
    private final Gson gson;

    public Dispatcher(){
        reimbursementView = new ReimbursementService();
        userView = new UserService();
        gson = new Gson().newBuilder().setPrettyPrinting().create();
    }

    /**
     *
     * @param servletClass the calling Class, to differentiate UserServlet, ReimbersmentServlet, etc.
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    public void dispatch(Class<? extends HttpServlet> servletClass, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String requestMethod = req.getMethod();

        GenericService service;
        Object jsonObject = null;

        // Map parameters to their first value
        HashMap<String,String> parameterMap = new HashMap<>();
        Class<?> modelClass;
        for(Map.Entry<String,String[]> m :req.getParameterMap().entrySet()){
            parameterMap.put(m.getKey(),m.getValue()[0]);
        }
        if(servletClass.equals(UserServlet.class)){
            service = userView;
            modelClass = User.class;
        } else if(servletClass.equals(ReimbursementServlet.class)){
            service = reimbursementView;
            modelClass = Reimbursement.class;
        } else{
            // if the class doesn't match a service
            RuntimeException e = new RuntimeException("no DAO assigned for this class");
            e.printStackTrace();
            throw e;
        }
        // if json is expected, try to parse the json with toJson()
        if(!requestMethod.equals("GET")){
            try {
                jsonObject = gson.fromJson(req.getReader(),modelClass);
                LOGGER.debug("JSON from the client was successfully parsed. Method: "+requestMethod);
            } catch (JsonSyntaxException | JsonIOException e) {
                LOGGER.error("Something occurred during JSON parsing. Is the JSON malformed? Method: "+requestMethod);
                e.printStackTrace();
            } catch (Exception e){
                LOGGER.error("Something else went wrong");
                e.printStackTrace();
            }
        }
        // dispatch to the service layer depending on the requestMethod
        switch(requestMethod){
            case "GET":
                if (parameterMap.get("id") != null){
                    gson.toJson(service.getById(Integer.parseInt(parameterMap.get("id"))),out);
                } else if (parameterMap.get("user_id") != null){
                    for(Object o : service.getByUserId(Integer.parseInt(parameterMap.get("user_id")))){
                        gson.toJson(o,out);
                        out.println();
                    }
                } else {
                    // get all
                    if(service.getList() != null){
                        for(Object o : service.getList()){
                            gson.toJson(o,out);
                            out.println();
                        }
                    }
                }
                break;
            case "PUT":
                service.createOrUpdate(jsonObject);
                out.print("PUT");
                break;
            case "POST":
                service.create(jsonObject);
                out.print("POSTED");
                break;
            case "DELETE":
                service.delete(jsonObject);
                out.print("DELETED, IF IT EXISTED IN THE FIRST PLACE");
                break;
            default:
                RuntimeException e = new RuntimeException("We don't support that Http method yet");
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
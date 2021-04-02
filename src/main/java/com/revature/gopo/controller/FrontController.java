package com.revature.gopo.controller;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//https://www.tutorialspoint.com/design_pattern/front_controller_pattern.htm
public class FrontController {
    private static final Logger LOGGER = Logger.getLogger(FrontController.class);

    private Dispatcher dispatcher;

    private static FrontController instance;

    private FrontController(){
        dispatcher = new Dispatcher();
    }

    public static FrontController getInstance(){
        if(instance == null){
            instance = new FrontController();
            return instance = new FrontController();
        } else {
            return instance;
        }
    }

    private boolean isAuthenticUser(){
        //TODO
        //System.out.println("User is authenticated successfully.");
        return true;
    }

    private void trackRequest(Class<? extends HttpServlet> clazz, HttpServletRequest req){
        LOGGER.debug("Page requested: " + req +
                System.lineSeparator() + "Method: " + req.getMethod() +
                System.lineSeparator() + "Servlet: " + clazz);
    }

    public void dispatchRequest(Class<? extends HttpServlet> clazz, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // each request
        trackRequest(clazz, req);

        //authenticate the user
        if(isAuthenticUser()){
            dispatcher.dispatch(clazz, req, resp);
        }
    }
}
package com.revature.gopo.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//https://www.tutorialspoint.com/design_pattern/front_controller_pattern.htm
public class FrontController {

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

    private void trackRequest(HttpServletRequest req){
        //TODO: replace this with a logger
        System.out.println("Page requested: " + req);
    }

    public void dispatchRequest(Class<? extends HttpServlet> clazz, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //log each request
        trackRequest(req);

        //authenticate the user
        if(isAuthenticUser()){
            dispatcher.dispatch(clazz, req, resp);
        }
    }
}
package com.revature.controller;

import com.revature.dao.ReimbursementDao;
import com.revature.dao.UserDao;

//https://www.tutorialspoint.com/design_pattern/front_controller_pattern.htm
public class Dispatcher {
    private ReimbursementDao reimbursementView;
    private UserDao userView;

    public Dispatcher(){
        reimbursementView = new ReimbursementDao();
        userView = new UserDao();
    }

    public void dispatch(String request){
        if(request.equalsIgnoreCase("USER")){
            // do user thing
        }
        else{
            // do something else
        }
    }
}
package com.revature.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//TODO: Currently not Utilized
public class SessionController {

    public void createSession(HttpServletRequest req, HttpServletResponse res) {
        // getSession(create?: boolean) will create a new session if one does not exist
        //      or will get the existing session. if create is set to false, then it
        //      will either get the existing session or return null without creating
        //      a new session.
        HttpSession session = req.getSession();

        session.setAttribute("role", "employee");

    }

    public void printSessionAttribute(HttpServletRequest req, HttpServletResponse res) {
        try {
            HttpSession session = req.getSession(false);
            if (session == null) {
                System.err.println("session was not available");
                res.getWriter().println("session was not available");
                return;
            }
            System.out.println(session.getAttribute("role"));

            res.getWriter().println(session.getAttribute("role"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void endSession(HttpServletRequest req, HttpServletResponse res) {
        HttpSession sess = req.getSession(false);
        if (sess == null) {
            System.err.println("session was already null");
        } else {
            sess.invalidate();
        }
    }
}

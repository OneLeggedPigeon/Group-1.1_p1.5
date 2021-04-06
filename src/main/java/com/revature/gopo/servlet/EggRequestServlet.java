package com.revature.gopo.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/egg")
public class EggRequestServlet extends HttpServlet {

    protected void doGet (HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/html" );
        ServletOutputStream sos = response.getOutputStream();

        sos.println( "<html><body>" );
        sos.println( "<img src='image/egg?height=50&width=50&red=12&green=20&blue=130'>" );
        sos.println( "<img src='image/egg?height=30&width=80&red=100&green=20&blue=100'>" );
        sos.println( "</body></html>" );
        sos.close();
    }
}
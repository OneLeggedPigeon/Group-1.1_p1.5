package com.revature.gopo.servlet;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/egg/generate")
public class EggServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
        String URLAfterWebDomain = request.getRequestURI();

        // Extract values from parameters
        int width = 150;
        int height = 150;
        int red = 0;
        int green = 0;
        int blue = 0;
        Color color = Color.WHITE;
        width = Integer.parseInt(request.getParameter("width"));
        height = Integer.parseInt(request.getParameter("height"));
        red = Integer.parseInt(request.getParameter("red"));
        green = Integer.parseInt(request.getParameter("green"));
        blue = Integer.parseInt(request.getParameter("blue"));
        color = new Color(red,green,blue);

        System.out.println(System.lineSeparator()+"Creating image");
        response.setContentType("image/jpeg"); //as far as I know, this works for PNG as well. You might want to change the mapping to /images/*.jpg if it's giving problems

        ServletOutputStream outStream = response.getOutputStream();

        BufferedOutputStream bout = new BufferedOutputStream(outStream);

        BufferedImage bi = new BufferedImage( 500, 500, BufferedImage.TYPE_INT_RGB );
        Graphics2D g = bi.createGraphics();
        g.setColor(color);
        g.fillOval( 30, 30, width, height );
        g.dispose();
        ImageIO.write( bi, "jpeg", outStream );

        bout.close();
        outStream.close();
    }

    protected void doPost ( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "image/jpeg" );
        ServletOutputStream sos = response.getOutputStream();

        BufferedImage bi = new BufferedImage( 500, 500, BufferedImage.TYPE_INT_RGB );
        Graphics2D g = bi.createGraphics();
        // g.setBackground(Color.WHITE);
        // g.clearRect(0, 0, 300, 300);

        String shapeselected = request.getParameter( "btn" );
        if ( shapeselected.equals( "Circle" ) )
        {
            g.setColor( Color.RED );
            g.fillOval( 30, 30, 150, 150 );
            g.dispose();
            ImageIO.write( bi, "jpeg", sos );
        }
        else
        {
            if ( shapeselected.equals( "Square" ) )
            {
                g.setColor( Color.GREEN );
                g.fillRect( 80, 80, 100, 100 );
                ImageIO.write( bi, "jpeg", sos );
            }
            else
            {
                g.setColor( Color.BLUE );
                g.fillRect( 80, 80, 200, 150 );
                ImageIO.write( bi, "jpeg", sos );
            }
        }

        sos.close();
    }
}
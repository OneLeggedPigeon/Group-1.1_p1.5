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

@WebServlet(urlPatterns = "/image/egg")
public class EggServlet extends HttpServlet {

    private static int WIDTH = 160;
    private static int HEIGHT = 260;
    private static int MARGIN = 1;

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
        String URLAfterWebDomain = request.getRequestURI();

        // percentage of max size
        int widthPerc;
        int heightPerc;
        // rgb color
        int red;
        int green;
        int blue;
        Color color;

        // Extract values from parameters
        try{
            widthPerc = Math.min(100, Math.abs(Integer.parseInt(request.getParameter("width"))));
        } catch (Exception e){
            //Using Default
            widthPerc = 50;
        }

        try{
            heightPerc = Math.min(100, Math.abs(Integer.parseInt(request.getParameter("height"))));
        } catch (Exception e){
            //Using Default
            heightPerc = 50;
        }

        try{
            red = Integer.parseInt(request.getParameter("red"));
            green = Integer.parseInt(request.getParameter("green"));
            blue = Integer.parseInt(request.getParameter("blue"));
        } catch (Exception e){
            //Using Default
            red = 10;
            green = 10;
            blue = 10;
        }
        color = new Color(red,green,blue);
        int width = widthPerc*WIDTH/100;
        int height = heightPerc*HEIGHT/100;

        System.out.println(System.lineSeparator()+"Creating image");
        response.setContentType("image/jpeg"); //as far as I know, this works for PNG as well. You might want to change the mapping to /images/*.jpg if it's giving problems

        ServletOutputStream outStream = response.getOutputStream();

        BufferedOutputStream bout = new BufferedOutputStream(outStream);

        BufferedImage bi = new BufferedImage( WIDTH + 2*MARGIN, HEIGHT + 2*MARGIN, BufferedImage.TYPE_INT_RGB );
        Graphics2D g = bi.createGraphics();
        g.setColor(color);

        g.fillOval( (WIDTH-width)/2+MARGIN, (HEIGHT-height)/2+MARGIN, width, height );
        g.dispose();
        ImageIO.write( bi, "jpeg", outStream );

        bout.close();
        outStream.close();
    }
}
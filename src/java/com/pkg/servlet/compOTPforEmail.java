/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.pkg.servlet;

import com.pkg.dao.UserDAO;
import com.pkg.entities.User;
import com.pkg.helper.Mail;
import com.pkg.helper.connectionProvider;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author raghavendra
 */
public class compOTPforEmail extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         //checking if session has key or not else redirected to login
         HttpSession session=request.getSession();
            User u=(User)session.getAttribute("user");
            if(u==null){
            response.sendRedirect("login.jsp");
            }
            String sysOTP=(String)session.getAttribute("otp");
            String userOTP=request.getParameter("getotp");
            if(new Mail().compOTP(sysOTP, userOTP)){
                String newEmail=(String)session.getAttribute("newemail");
                if(new UserDAO(connectionProvider.getConnection()).updateUserProfile("user", "email", newEmail, u.getEmail())){
                    System.out.println("email changed successfully");
                    u.setEmail(newEmail);
                }
                response.sendRedirect("editprofile.jsp");
            }else{
                session.setAttribute("error", "invalid OTP");
                response.sendRedirect("submitOTPonchangeEmail.jsp");
            }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

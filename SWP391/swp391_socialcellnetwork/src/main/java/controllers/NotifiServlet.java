/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.FriendDAO;
import dal.NotifiDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import models.Notification;
import models.User;

/**
 *
 * @author khang
 */
public class NotifiServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NotifiServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NotifiServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        LocalDateTime now = LocalDateTime.now();
        Timestamp date = Timestamp.valueOf(now);
        String action = request.getParameter("action");
        int notificationId = Integer.parseInt(request.getParameter("notificationId"));
        int relationId = Integer.parseInt(request.getParameter("relatiID"));
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");
        if ("accept".equals(action)) {
            int friendId = FriendDAO.INSTANCE.getFriendIdFromRelation(currentUser.getUser_id(), relationId);
            NotifiDAO.INSTANCE.acceptFriendRequest(notificationId);
            String description = "Your connection request to " + currentUser.getF_name() + " " + currentUser.getL_name() + " has been accepted.";
            NotifiDAO.INSTANCE.createNotification(friendId, 2, date, description, relationId);
        } else if ("reject".equals(action)) {
            NotifiDAO.INSTANCE.rejectFriendRequest(notificationId, relationId);
        } else {
            NotifiDAO.INSTANCE.deleteNotification(notificationId);
        }
        request.getSession().removeAttribute("notifications");
        List<Notification> notifications = NotifiDAO.INSTANCE.getAllNotificationsForUser(currentUser.getUser_id());
        session.setAttribute("notifications", notifications);

        response.sendRedirect(request.getContextPath() + "/home");
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

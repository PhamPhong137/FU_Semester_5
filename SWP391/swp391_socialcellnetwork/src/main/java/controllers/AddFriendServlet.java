/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.ConversationDAO;
import dal.FriendDAO;
import dal.NotifiDAO;
import dal.TagMemberDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import models.TagMember;
import models.User;

/**
 *
 * @author Admin
 */
public class AddFriendServlet extends HttpServlet {

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
            out.println("<title>Servlet AddFriendServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddFriendServlet at " + request.getContextPath() + "</h1>");
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
        String searchQuery = request.getParameter("searchQuery");
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");
        int currentUserId = currentUser.getUser_id();
        boolean isValidQuery = searchQuery.matches("[0-9]{10}") || searchQuery.matches("[A-Za-z]+");
        if (!isValidQuery) {
            request.setAttribute("searchQuery", searchQuery);
            request.setAttribute("currentUser", currentUser);
            request.setAttribute("friends", null);
            request.setAttribute("data", FriendDAO.INSTANCE);
            request.setAttribute("currentUserId", currentUserId);
            request.getRequestDispatcher("views/listFriend.jsp").forward(request, response);
            return;
        }
        boolean isFriendSearch = !searchQuery.matches("[0-9]+");
        List<User> friends = FriendDAO.INSTANCE.SearchAllFriend(searchQuery, currentUserId, isFriendSearch);
        request.setAttribute("searchQuery", searchQuery);
        request.setAttribute("currentUser", currentUser);
        request.setAttribute("friends", friends);
        request.setAttribute("data", FriendDAO.INSTANCE);
        request.setAttribute("currentUserId", currentUserId);
        request.getRequestDispatcher("views/listFriend.jsp").forward(request, response);
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
        String searchQuery = request.getParameter("searchQuery");
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");
        int currentUserId = currentUser.getUser_id();
        String friendId_raw = request.getParameter("friendId");
        int friendId = 0;
        LocalDateTime now = LocalDateTime.now();
        Timestamp date = Timestamp.valueOf(now);
        try {
            friendId = Integer.parseInt(friendId_raw);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String action = request.getParameter("action");
        int relatiID = FriendDAO.INSTANCE.getRelationId(currentUserId, friendId);
        if ("add".equals(action)) {
            int relationId = FriendDAO.INSTANCE.addFriend(currentUserId, friendId);
            //Create notification
            if (relationId != -1) {
                String description = "You have a new connection request from " + currentUser.getF_name() + " " + currentUser.getL_name();
                NotifiDAO.INSTANCE.createNotification(friendId, 1, date, description, relationId);
            }
            //check conversation exist
            if (ConversationDAO.INSTANCE.checkExistedConversationPair(currentUserId, friendId) == 0) {
                //Create conversation
                int conversationId = ConversationDAO.INSTANCE.addConversationPair(currentUserId + " " + friendId,
                        currentUserId + " " + friendId);
                ConversationDAO.INSTANCE.addConversationMember(0, friendId, conversationId);
                ConversationDAO.INSTANCE.addConversationMember(0, currentUserId, conversationId);
                ConversationDAO.INSTANCE.addConversationMessage("Hello my new friend!", date, currentUserId, conversationId);
            }
        } else if ("delete".equals(action)) {
            FriendDAO.INSTANCE.deleteFriend(currentUserId, friendId);
            TagMemberDAO.INSTANCE.deleteTagEventByRelation(currentUserId, friendId);
        } else if ("accept".equals(action)) {
            int notificationId = NotifiDAO.INSTANCE.getFriendRequestNotificationId(currentUserId, friendId);
            NotifiDAO.INSTANCE.acceptFriendRequest(notificationId);
            String description = "Your connection request to " + currentUser.getF_name() + " " + currentUser.getL_name() + " has been accepted.";
            NotifiDAO.INSTANCE.createNotification(friendId, 2, date, description, relatiID);
        } else if ("reject".equals(action)) {
            int notificationId = NotifiDAO.INSTANCE.getFriendRequestNotificationId(currentUserId, friendId);
            NotifiDAO.INSTANCE.rejectFriendRequest(notificationId, relatiID);
        } else {
            int notificationId = NotifiDAO.INSTANCE.getFriendRequestNotificationId(currentUserId, friendId);
            NotifiDAO.INSTANCE.rejectFriendRequest(notificationId, relatiID);
        }
        response.sendRedirect("addfriend?searchQuery=" + searchQuery);
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

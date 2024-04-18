/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import static controllers.BoxChatServlet.sortByDateChat;
import dal.ConversationDAO;
import dal.FriendDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import models.ConversationMessage;
import models.User;

/**
 *
 * @author Fpt
 */
public class UpdateBoxChatServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateBoxChatServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateBoxChatServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");
        int currentUserId = currentUser.getUser_id();
        int friendId = Integer.parseInt(request.getParameter("friendId"));
        String friendName = request.getParameter("friendName");
        String description = request.getParameter("description");
        LocalDateTime now = LocalDateTime.now();
        Timestamp date = Timestamp.valueOf(now);
        if (ConversationDAO.INSTANCE.checkExistedConversationPair(currentUserId, friendId) == 0) {
            //Create conversation
            int conversationId = ConversationDAO.INSTANCE.addConversationPair(currentUserId + " " + friendId,
                    currentUserId + " " + friendId);
            ConversationDAO.INSTANCE.addConversationMember(0, friendId, conversationId);
            ConversationDAO.INSTANCE.addConversationMember(0, currentUserId, conversationId);
            ConversationDAO.INSTANCE.addConversationMessage(description, date, currentUserId, conversationId);
        }else{
            int conversationChoosenId = ConversationDAO.INSTANCE.checkExistedConversationPair(currentUser.getUser_id(), friendId);
            ConversationDAO.INSTANCE.addConversationMessage(description, date, currentUserId, conversationChoosenId);
        }
        int conversationChoosenId = ConversationDAO.INSTANCE.checkExistedConversationPair(currentUser.getUser_id(), friendId);
        User choosenConversationFriend = UserDAO.INSTANCE.getUserByUserId(friendId);
        List<ConversationMessage> allConversationMessage = ConversationDAO.INSTANCE.SearchAllConversationMessage(conversationChoosenId);
        HashMap<ConversationMessage, User> allConversationMessageChat = new HashMap<>();
        for (ConversationMessage conversationMessage : allConversationMessage) {
            if (conversationMessage.getSent_by() == choosenConversationFriend.getUser_id()) {
                allConversationMessageChat.put(conversationMessage, choosenConversationFriend);
            } else {
                allConversationMessageChat.put(conversationMessage, currentUser);
            }
        }
        // sắp xếp allConversationMessageChat
        TreeMap<ConversationMessage, User> sortedConversationMessages = sortByDateChat(allConversationMessageChat);
        boolean areFriend = FriendDAO.INSTANCE.areFriends(currentUser.getUser_id(), friendId);
        request.setAttribute("areFriend", areFriend);
        request.setAttribute("allConversationMessageChat", sortedConversationMessages);
        request.setAttribute("choosenConversationFriend", choosenConversationFriend);
        request.setAttribute("conversationChoosenId", conversationChoosenId);
        request.getRequestDispatcher("boxchat").forward(request, response);
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
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");
        int send_by = currentUser.getUser_id();
        if(request.getParameter("send_by") != null){
            send_by = Integer.parseInt(request.getParameter("send_by") );
        }
        String description = request.getParameter("description");
        int conversationChoosenId = Integer.parseInt(request.getParameter("conversationChoosenId"));
        int user_id = currentUser.getUser_id();
        LocalDateTime now = LocalDateTime.now();
        Timestamp date = Timestamp.valueOf(now);
        ConversationDAO.INSTANCE.addConversationMessage(description, date, send_by, conversationChoosenId);
        request.getRequestDispatcher("boxchat").forward(request, response);
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


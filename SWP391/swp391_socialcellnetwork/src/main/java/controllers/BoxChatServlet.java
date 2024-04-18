/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.ConversationDAO;
import dal.FamilyDAO;
import dal.FriendDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import models.*;

/**
 *
 * @author Fpt
 */
public class BoxChatServlet extends HttpServlet {

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
            out.println("<title>Servlet BoxChatServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BoxChatServlet at " + request.getContextPath() + "</h1>");
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
        if(request.getParameter("update") != null){
            doPost(request, response);
        }else{
            HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        String name = request.getParameter("name");
        // xử lí phần friend
        List<User> listFriend = ConversationDAO.INSTANCE.SearchAllFriendChattingPair(currentUser.getUser_id());
        HashMap<ConversationMessage, User> listFriendAndTime = new HashMap<>();
        for (User user : listFriend) {
            int conversationId = ConversationDAO.INSTANCE.checkExistedConversationPair(currentUser.getUser_id(), user.getUser_id());
            ConversationMessage conversationMessage = ConversationDAO.INSTANCE.SearchNewChatFriendChattingPair(conversationId);
            listFriendAndTime.put(conversationMessage, user);
        }
        HashMap<ConversationMessage, User> listSearchFriend = new HashMap<>();
        //search friend
        if (name == null || name.isEmpty()) {
            TreeMap<ConversationMessage, User> listFriendAndTimeSorted = sortByDateMember(listFriendAndTime);
            request.setAttribute("listFriend", listFriendAndTimeSorted);
        } else {
            for (Map.Entry<ConversationMessage, User> entry : listFriendAndTime.entrySet()) {
                String s = entry.getValue().getF_name() + " " + entry.getValue().getL_name();
                String ss = entry.getValue().getF_name() + entry.getValue().getL_name();
                if (s.contains(name.trim()) || ss.contains(name.trim())) {
                    listSearchFriend.put(entry.getKey(), entry.getValue());
                }
            }

            TreeMap<ConversationMessage, User> listSearchFriendSorted = sortByDateMember(listSearchFriend);
            request.setAttribute("listFriend", listSearchFriendSorted);
        }

        //xử lí phần family
        List<FamilyInfo> listFamily = ConversationDAO.INSTANCE.SearchAllFamilyChatting(currentUser.getUser_id());
        HashMap<ConversationMessage, FamilyInfo> listFamilyAndTime = new HashMap<>();
        for (FamilyInfo familyInfo : listFamily) {
            int conversationId = ConversationDAO.INSTANCE.checkExistedFamilyConversation(familyInfo.getFamily_id());
            ConversationMessage conversationMessage = ConversationDAO.INSTANCE.SearchNewChatFriendChattingPair(conversationId);
            listFamilyAndTime.put(conversationMessage, familyInfo);
        }
        HashMap<ConversationMessage, FamilyInfo> listSearchFamily = new HashMap<>();
        //search family
        if (name == null || name.isEmpty()) {
            TreeMap<ConversationMessage, FamilyInfo> listFamilyAndTimeSorted = sortByDateFamily(listFamilyAndTime);
            request.setAttribute("listFamily", listFamilyAndTimeSorted);
        } else {
            for (Map.Entry<ConversationMessage, FamilyInfo> entry : listFamilyAndTime.entrySet()) {
                String s = entry.getValue().getName();
                if (s.toLowerCase().contains(name.trim()) || s.toUpperCase().contains(name.trim())) {
                    listSearchFamily.put(entry.getKey(), entry.getValue());
                }
            }
            TreeMap<ConversationMessage, FamilyInfo> listSearchFamilySorted = sortByDateFamily(listSearchFamily);
            request.setAttribute("listFamily", listSearchFamilySorted);

        }
        request.setAttribute("name", name);
        request.getRequestDispatcher("views/boxchat.jsp").forward(request, response);
        }
        
    }

    public static TreeMap<ConversationMessage, FamilyInfo> sortByDateFamily(HashMap<ConversationMessage, FamilyInfo> inputMap) {
        // Khởi tạo một Comparator riêng để so sánh ConversationMessage.getDate()
        Comparator<ConversationMessage> comparator = new Comparator<ConversationMessage>() {
            @Override
            public int compare(ConversationMessage message1, ConversationMessage message2) {
                // Lấy ngày của ConversationMessage
                long date1 = message1.getDate().getTime();
                long date2 = message2.getDate().getTime();

                // So sánh ngày và đảo ngược thứ tự
                return Long.compare(date2, date1);
            }
        };

        // Sử dụng Comparator này để khởi tạo TreeMap mới
        TreeMap<ConversationMessage, FamilyInfo> sortedMap = new TreeMap<>(comparator);

        // Đưa tất cả các phần tử từ inputMap vào sortedMap, TreeMap sẽ tự động sắp xếp lại
        sortedMap.putAll(inputMap);

        return sortedMap;
    }

    public static TreeMap<ConversationMessage, User> sortByDateMember(HashMap<ConversationMessage, User> inputMap) {
        // Khởi tạo một Comparator riêng để so sánh ConversationMessage.getDate()
        Comparator<ConversationMessage> comparator = new Comparator<ConversationMessage>() {
            @Override
            public int compare(ConversationMessage message1, ConversationMessage message2) {
                // Lấy ngày của ConversationMessage
                long date1 = message1.getDate().getTime();
                long date2 = message2.getDate().getTime();

                // So sánh ngày và đảo ngược thứ tự
                return Long.compare(date2, date1);
            }
        };

        // Sử dụng Comparator này để khởi tạo TreeMap mới
        TreeMap<ConversationMessage, User> sortedMap = new TreeMap<>(comparator);

        // Đưa tất cả các phần tử từ inputMap vào sortedMap, TreeMap sẽ tự động sắp xếp lại
        sortedMap.putAll(inputMap);

        return sortedMap;
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
        //Friend
        if (request.getParameter("choosenUserId") != null) {
            int friendId = Integer.parseInt(request.getParameter("choosenUserId"));
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
            Conversation conversationStatus = ConversationDAO.INSTANCE.SearchConversationById(conversationChoosenId);
            request.setAttribute("areFriend", areFriend);
            request.setAttribute("conversationStatus", conversationStatus);
            request.setAttribute("allConversationMessageChat", sortedConversationMessages);
            request.setAttribute("choosenConversationFriend", choosenConversationFriend);
            request.setAttribute("conversationChoosenId", conversationChoosenId);
        } else {
            //Family
            int familyId = Integer.parseInt(request.getParameter("choosenFamilyId"));
            int conversationChoosenId = ConversationDAO.INSTANCE.checkExistedFamilyConversation(familyId);
            FamilyInfo choosenConversationFamily = FamilyDAO.INSTANCE.getFamilyById(familyId);
            List<ConversationMessage> allConversationMessage = ConversationDAO.INSTANCE.SearchAllConversationMessage(conversationChoosenId);
            HashMap<ConversationMessage, User> allConversationMessageChat = new HashMap<>();
            for (ConversationMessage conversationMessage : allConversationMessage) {
                User user = UserDAO.INSTANCE.getUserByUserId(conversationMessage.getSent_by());
                allConversationMessageChat.put(conversationMessage, user);
            }
            // sắp xếp allConversationMessageChat
            TreeMap<ConversationMessage, User> sortedConversationMessages = sortByDateChat(allConversationMessageChat);
            Conversation conversationStatus = ConversationDAO.INSTANCE.SearchConversationById(conversationChoosenId);
            request.setAttribute("conversationStatus", conversationStatus);
            request.setAttribute("allConversationMessageChat", sortedConversationMessages);
            request.setAttribute("choosenConversationFamily", choosenConversationFamily);
            request.setAttribute("conversationChoosenId", conversationChoosenId);
        }
        doGet(request, response);
    }

    public static TreeMap<ConversationMessage, User> sortByDateChat(HashMap<ConversationMessage, User> inputMap) {
        // Khởi tạo một Comparator riêng để so sánh ConversationMessage.getDate()
        Comparator<ConversationMessage> comparator = new Comparator<ConversationMessage>() {
            @Override
            public int compare(ConversationMessage message1, ConversationMessage message2) {
                // Lấy ngày của ConversationMessage
                long date1 = message1.getDate().getTime();
                long date2 = message2.getDate().getTime();

                // So sánh ngày và đảo ngược thứ tự
                return Long.compare(date1, date2);
            }
        };

        // Sử dụng Comparator này để khởi tạo TreeMap mới
        TreeMap<ConversationMessage, User> sortedMap = new TreeMap<>(comparator);

        // Đưa tất cả các phần tử từ inputMap vào sortedMap, TreeMap sẽ tự động sắp xếp lại
        sortedMap.putAll(inputMap);

        return sortedMap;
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

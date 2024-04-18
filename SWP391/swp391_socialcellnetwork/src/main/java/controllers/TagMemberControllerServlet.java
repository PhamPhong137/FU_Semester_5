/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import com.google.gson.Gson;
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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import models.TagMember;
import models.User;

/**
 *
 * @author PC-Phong
 */
public class TagMemberControllerServlet extends HttpServlet {

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
            out.println("<title>Servlet TagMemberControllerServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TagMemberControllerServlet at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        PrintWriter out = response.getWriter();

        String eventid = request.getParameter("eventid");
        String userid = request.getParameter("userid");

        List<TagMember> loadTagmemberByEventId = TagMemberDAO.INSTANCE.loadTagmemberByEventId(Integer.parseInt(eventid));
        List<User> loadFriendByUserId = UserDAO.INSTANCE.loadFriendByUserId(Integer.parseInt(userid));

        out.print(""
                + "      <label>Edit your tag friends:</label>\n"
                + "      <div>\n"
                + "        <select name=\"tagMemberSelect\" id=\"tagMemberSelect" + eventid + "\" class=\"anymember\" multiple style=\"width: 100%;\">\n");

        for (User tagMember : loadFriendByUserId) {

            boolean isSelected = loadTagmemberByEventId.stream().anyMatch(friend -> friend.getUser_id() == tagMember.getUser_id());

            out.print("          <option value=\"" + tagMember.getUser_id() + "\"" + (isSelected ? " selected" : "") + ">" + tagMember.getF_name() + " " + tagMember.getL_name() + "</option>\n");
        }

        out.print("</select>\n"
                + "</div>\n"
        );
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
        response.setContentType("text/html;charset=UTF-8");
        LocalDateTime now = LocalDateTime.now();
        Timestamp date = Timestamp.valueOf(now);
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        PrintWriter out = response.getWriter();

        String[] selectedMembers = request.getParameterValues("selectedMembers[]");
        String eventid_row = request.getParameter("eventid");
        int event_id = Integer.parseInt(eventid_row);
        String title = request.getParameter("description");
        String eventDate_str = request.getParameter("event_id");
        LocalDate eventDate = LocalDate.parse(eventDate_str);
        LocalDateTime eventDateTime = eventDate.atStartOfDay();
        Timestamp eventTimestamp = Timestamp.valueOf(eventDateTime);
        TagMemberDAO.INSTANCE.deleteTagEventByEventId(event_id);
        NotifiDAO.INSTANCE.deleteNotificationEvent(event_id);
        if (selectedMembers != null) {
            for (int i = 0; i < selectedMembers.length; i++) {
                TagMemberDAO.INSTANCE.addTagEvent(event_id, Integer.parseInt(selectedMembers[i]));
                int relatiID = FriendDAO.INSTANCE.getRelationId(currentUser.getUser_id(), Integer.parseInt(selectedMembers[i]));
                String notificationDescription = "Update: You have been tagged in the event: '" + title + "' by " + currentUser.getF_name() + " " + currentUser.getL_name() + ".";
                NotifiDAO.INSTANCE.createNotification(Integer.parseInt(selectedMembers[i]), 3, date, notificationDescription, relatiID);
                NotifiDAO.INSTANCE.createNotificationRemineEvent(Integer.parseInt(selectedMembers[i]), 3, date, notificationDescription, eventTimestamp, relatiID, event_id);
            }
        }

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

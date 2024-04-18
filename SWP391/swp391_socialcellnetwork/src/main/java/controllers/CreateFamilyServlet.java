/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.ConversationDAO;
import dal.FamilyDAO;
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
import models.*;

/**
 *
 * @author Admin
 */
public class CreateFamilyServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CreateFamilyServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateFamilyServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String familyname = request.getParameter("familyname");
        String imageUrl = request.getParameter("imageurl");
        if (imageUrl == null || imageUrl.isEmpty()) {
            imageUrl = "familydefault.jpg";
        }
        String introduction = request.getParameter("introduction");
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");
        int currentUserId = currentUser.getUser_id();
        int familyId = FamilyDAO.INSTANCE.addFamily(familyname, imageUrl, introduction);
        int conversationId = ConversationDAO.INSTANCE.addConversationGroup(familyId, familyname, imageUrl);
        ConversationDAO.INSTANCE.addConversationMember(1, currentUserId, conversationId);
        LocalDateTime now = LocalDateTime.now();
        Timestamp date = Timestamp.valueOf(now);
        ConversationDAO.INSTANCE.addConversationMessage("Hello every one!", date, currentUserId, conversationId);
        if (familyId != -1) {
            boolean memberAdded = FamilyDAO.INSTANCE.addFamilyMember(familyId, currentUserId, 1);
            if (memberAdded == true) {
                boolean familyExists = FamilyDAO.INSTANCE.checkFamilyExists(familyId, currentUserId);
                if (familyExists) {
                    FamilyInfo family = FamilyDAO.INSTANCE.getFamilyById(familyId);
                    request.getSession().removeAttribute("listfamily");
                    List<FamilyInfo> listfamily = FamilyDAO.INSTANCE.getAllFamilysByUserId(currentUser.getUser_id());
                    session.setAttribute("listfamily", listfamily);
                    response.sendRedirect("familydetail?familyId=" + familyId);
                }
            }
        }
    }
}

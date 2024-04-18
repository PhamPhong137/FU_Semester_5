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
import java.util.List;
import models.FamilyInfo;
import models.User;

/**
 *
 * @author Admin
 */
public class FamilyAdminServlet extends HttpServlet {

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
            out.println("<title>Servlet FamilyAdminServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FamilyAdminServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        String action = request.getParameter("action");
        int familyId = Integer.parseInt(request.getParameter("familyid"));
        if ("update".equals(action)) {
            String name = request.getParameter("familyname").trim();
            String img = "";
            if (request.getParameter("imageurl") == null || request.getParameter("imageurl").isEmpty()) {
                img = request.getParameter("image").trim();
            } else {
                img = request.getParameter("imageurl").trim();
            }
            String des = request.getParameter("introduction").trim();
            FamilyDAO.INSTANCE.editFamily(familyId, name, img, des);
            request.getSession().removeAttribute("listfamily");
            List<FamilyInfo> listfamily = FamilyDAO.INSTANCE.getAllFamilysByUserId(currentUser.getUser_id());
            session.setAttribute("listfamily", listfamily);
            response.sendRedirect("familydetail?familyId=" + familyId);
        } else if ("delete".equals(action)) {
            FamilyDAO.INSTANCE.removeFamilyByFamilyId(familyId);
            request.getSession().removeAttribute("listfamily");
            List<FamilyInfo> listfamily = FamilyDAO.INSTANCE.getAllFamilysByUserId(currentUser.getUser_id());
            session.setAttribute("listfamily", listfamily);
            response.sendRedirect("home");
        } else if ("editMember".equals(action)) {
            String[] userIds = request.getParameterValues("userIds");
            if (userIds != null) {
                for (String userIdStr : userIds) {
                    int userId = Integer.parseInt(userIdStr);
                    String memberAction = request.getParameter("actionForMember_" + userId);
                    if ("delete".equals(memberAction)) {
                        int conversationId = ConversationDAO.INSTANCE.checkExistedFamilyConversation(familyId);
                        ConversationDAO.INSTANCE.removeConversationMember(userId, conversationId);
                        FamilyDAO.INSTANCE.removeFamilyMember(familyId, userId);
                    } else if ("admin".equals(memberAction)) {
                        FamilyDAO.INSTANCE.updateFamilyMemberRole(familyId, userId, 1);
                    }
                }
            }
            response.sendRedirect("familydetail?familyId=" + familyId);
        } else if ("addMember".equals(action)) {
            int userIdToAdd = Integer.parseInt(request.getParameter("userId"));
            int conversationId = ConversationDAO.INSTANCE.checkExistedFamilyConversation(familyId);
            ConversationDAO.INSTANCE.addConversationMember(0, userIdToAdd, conversationId);
            request.getSession().removeAttribute("listfamily");
            List<FamilyInfo> listfamily = FamilyDAO.INSTANCE.getAllFamilysByUserId(currentUser.getUser_id());
            session.setAttribute("listfamily", listfamily);
            boolean memberAdded = FamilyDAO.INSTANCE.addFamilyMember(familyId, userIdToAdd, 0);
        } else if ("searchMember".equals(action)) {
            String searchQuery = request.getParameter("searchQuery");
            if (searchQuery == null || searchQuery.trim().isEmpty()) {
                PrintWriter out = response.getWriter();
                out.print("<div class='user-entry' style='margin-bottom: 10px; padding: 5px; color: red;'>No results found.</div>");
                out.flush();
            } else {
                List<User> users = FamilyDAO.INSTANCE.searchByNameOrPhoneNumber(searchQuery, currentUser.getUser_id(), familyId);
                PrintWriter out = response.getWriter();
                if (!users.isEmpty()) {
                    for (User user : users) {
                        out.println("<div class='user-entry' data-user-id='" + user.getUser_id() + "' style='margin-bottom: 10px; padding: 10px; border: 1px solid #ddd; border-radius: 5px; display: flex; flex-direction: column; align-items: flex-start;'>"
                                + "<div style='display: flex; align-items: center; width: 100%;'><span style='white-space: nowrap; overflow: hidden; text-overflow: ellipsis; max-width: 50%; margin-right: 10px;'>" + user.getF_name() + " " + user.getL_name() + "</span>"
                                + "<span style='flex-grow: 1; text-align: right;'>" + user.getPhone_number() + "</span> </div>"
                                + "<button class='add-user-btn' onclick='addMember(" + user.getUser_id() + ")' style='margin-top: 5px; padding: 5px 10px; border: none; border-radius: 5px; background-color: #4CAF50; color: white; align-self: flex-end;'>Add</button></div>");
                    }
                } else {
                    out.print("<div class='user-entry' style='margin-bottom: 10px; padding: 5px; color: red;'>No results found.</div>");
                }
                out.flush();
            }
        } else {
            response.sendRedirect("errorPage");
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

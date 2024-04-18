/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.AccountDAO;
import dal.CommentDAO;
import dal.FamilyDAO;
import dal.StatusDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import models.FamilyInfo;
import models.FamilyMember;
import models.Status;
import models.User;

/**
 *
 * @author Admin
 */
public class FamilyDetailServlet extends HttpServlet {

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
            out.println("<title>Servlet FamilyDetailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FamilyDetailServlet at " + request.getContextPath() + "</h1>");
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
        int familyId = Integer.parseInt(request.getParameter("familyId"));
        boolean familyExists = FamilyDAO.INSTANCE.checkFamilyExists(familyId, currentUser.getUser_id());
        if (familyExists) {
            FamilyMember familymember = FamilyDAO.INSTANCE.getFamilyMemberById(familyId, currentUser.getUser_id());
            FamilyInfo family = FamilyDAO.INSTANCE.getFamilyById(familyId);
            List<User> membersWithRole = FamilyDAO.INSTANCE.getFamilyMembersByRole(familyId, 0);
            List<User> listAdmin = FamilyDAO.INSTANCE.getFamilyMembersByRole(familyId, 1);
            //status
            StatusDAO.INSTANCE.loadStatusByFamilyId(familyId);
            List<Status> familyStatusList = StatusDAO.INSTANCE.getStatusInfoByFamilyId();
            List<User> userOfStatus = new ArrayList<>();
            List<Integer> countReaction = new ArrayList<>();
            List<Integer> countComment = new ArrayList<>();
            List<Boolean> checkLike = new ArrayList<>();
            for (Status s : familyStatusList) {
                userOfStatus.add(AccountDAO.INSTANCE.getUserById(s.getUser_id()));
                countReaction.add(StatusDAO.INSTANCE.countReaction(s.getStatus_id()));
                countComment.add(CommentDAO.INSTANCE.countComment(s.getStatus_id()));
                int check = StatusDAO.INSTANCE.checkReaction(currentUser.getUser_id(), s.getStatus_id());
                if (check != -1) {
                    checkLike.add(Boolean.TRUE);
                } else {
                    checkLike.add(Boolean.FALSE);
                }
            }
            request.setAttribute("familyStatusList", familyStatusList);
            request.setAttribute("currentUser", currentUser);
            request.setAttribute("user", userOfStatus);
            request.setAttribute("count", countReaction);
            request.setAttribute("countComment", countComment);
            request.setAttribute("checkLike", checkLike);
            request.setAttribute("familyId", familyId);
            
            request.setAttribute("family", family);
            request.setAttribute("familymember", familymember);
            request.setAttribute("membersWithRole", membersWithRole);
            request.setAttribute("listAdmin", listAdmin);
            request.getRequestDispatcher("views/family.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("views/accesscontrol.jsp").forward(request, response);
        }
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

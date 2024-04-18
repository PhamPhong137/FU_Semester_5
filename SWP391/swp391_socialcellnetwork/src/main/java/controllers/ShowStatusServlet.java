/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.AccountDAO;
import dal.CommentDAO;
import models.Status;
import dal.StatusDAO;
import dal.TagMemberStatusDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import models.User;

/**
 *
 * @author ACER
 */
public class ShowStatusServlet extends HttpServlet {

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
            out.println("<title>Servlet StatusController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StatusController at " + request.getContextPath() + "</h1>");
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
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        StatusDAO.INSTANCE.loadStatus(currentUser.getUser_id());
        List<Status> showAllStatus = StatusDAO.INSTANCE.getStatusInfo();
        List<User> userOfStatus = new ArrayList<>();
        List<Integer> countReaction = new ArrayList<>();
        List<Integer> countComment = new ArrayList<>();
        List<Boolean> checkLike = new ArrayList<>();
        List<List> tagedMembers = new ArrayList<>();
        List<User> loadFriendByUserId = UserDAO.INSTANCE.loadFriendByUserId(currentUser.getUser_id());

        for (Status s : showAllStatus) {
            userOfStatus.add(AccountDAO.INSTANCE.getUserById(s.getUser_id()));
            countReaction.add(StatusDAO.INSTANCE.countReaction(s.getStatus_id()));
            countComment.add(CommentDAO.INSTANCE.countComment(s.getStatus_id()));
            int check = StatusDAO.INSTANCE.checkReaction(currentUser.getUser_id(), s.getStatus_id());
            tagedMembers.add(TagMemberStatusDAO.INSTANCE.loadTagStatus(s.getStatus_id()));

            if (check != -1) {
                checkLike.add(Boolean.TRUE);
            } else {
                checkLike.add(Boolean.FALSE);
            }
        }
        request.setAttribute("showAllStatus", showAllStatus);
        request.setAttribute("currentUser", currentUser);
        request.setAttribute("user", userOfStatus);
        request.setAttribute("count", countReaction);
        request.setAttribute("countComment", countComment);
        request.setAttribute("checkLike", checkLike);
        request.setAttribute("taged", tagedMembers);
        request.setAttribute("loadFriendByUserId", loadFriendByUserId);

        String page = request.getParameter("page");
        if (page == null || page.equals("status")) {
            request.getRequestDispatcher("views/status.jsp").forward(request, response);
        } else if (page.equals("profile")) {
            response.sendRedirect("profile");
        } else {
            response.sendRedirect("familydetail?familyId=" + page);
        }

    }

    /**
     * Handles UploadStautus.
     *
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        int id = Integer.parseInt(request.getParameter("id"));
        String description = request.getParameter("content");
        String img;
        String img_raw = request.getParameter("image");
        if ((img_raw == null || img_raw.isEmpty())) {
            img = request.getParameter("uploadedImage");
            if (img.equals("")) {
                img = null;
            }
        } else {
            img = img_raw;
        }
        StatusDAO.INSTANCE.updateStatus(id, img, description);

        String page = request.getParameter("page");
        if (page.equals("status")) {
            response.sendRedirect("showstatus");
        } else if (page.equals("profile")) {
            response.sendRedirect("profile");
        } else {
            response.sendRedirect("familydetail?familyId=" + page);
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

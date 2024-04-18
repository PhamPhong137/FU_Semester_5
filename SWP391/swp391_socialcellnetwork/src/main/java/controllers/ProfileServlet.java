/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.CommentDAO;
import dal.FamilyDAO;
import dal.FriendDAO;
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
import java.net.URLEncoder;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import models.FamilyInfo;
import models.Status;
import models.User;

/**
 *
 * @author PC-Phong
 */
public class ProfileServlet extends HttpServlet {

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
            out.println("<title>Servlet Profile</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Profile at " + request.getContextPath() + "</h1>");
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
        User currentUser = new User();
        HttpSession session = request.getSession();
        if (request.getParameter("user_id") != null) {
            int user_id = Integer.parseInt(request.getParameter("user_id"));
            currentUser = UserDAO.INSTANCE.getUserByUserId(user_id);
        } else {
            currentUser = (User)session.getAttribute("currentUser");
        }
        List<User> friends = FriendDAO.INSTANCE.SearchAllFriendOfUserProfile(currentUser.getUser_id());
        StatusDAO.INSTANCE.loadStatusByUserId(currentUser.getUser_id());
        List<Status> listStatusByUserId = StatusDAO.INSTANCE.getStatusInfoByUserId();
        List<List> tagedMembers = new ArrayList<>();
        List<Integer> countReaction = new ArrayList<>();
        List<Integer> countComment = new ArrayList<>();
        List<Boolean> checkLike = new ArrayList<>();
         List<User> loadFriendByUserId = UserDAO.INSTANCE.loadFriendByUserId(currentUser.getUser_id());
        for (Status s : listStatusByUserId) {
            countReaction.add(StatusDAO.INSTANCE.countReaction(s.getStatus_id()));
            countComment.add(CommentDAO.INSTANCE.countComment(s.getStatus_id()));
            User sessionusser = (User)session.getAttribute("currentUser");
            int check = StatusDAO.INSTANCE.checkReaction(sessionusser.getUser_id(), s.getStatus_id());
            tagedMembers.add(TagMemberStatusDAO.INSTANCE.loadTagStatus(s.getStatus_id()));
            if (check != -1) {
                checkLike.add(Boolean.TRUE);
            } else {
                checkLike.add(Boolean.FALSE);
            }
        }
        String processUpdate = request.getParameter("process");
        List<FamilyInfo> listfamily = FamilyDAO.INSTANCE.getAllFamilysByUserId(currentUser.getUser_id());
        request.setAttribute("listfamily", listfamily);
        request.setAttribute("process", processUpdate);
        request.setAttribute("loadFriendByUserId", loadFriendByUserId);
        request.setAttribute("taged", tagedMembers);
        request.setAttribute("count", countReaction);
        request.setAttribute("countComment", countComment);
        request.setAttribute("checkLike", checkLike);
        request.setAttribute("listStatusByUserId", listStatusByUserId);
        request.setAttribute("currentUser", currentUser);
        request.setAttribute("friends", friends);
        request.getRequestDispatcher("views/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id_raw = request.getParameter("id");
        String gender_raw = request.getParameter("gender");
        PrintWriter out = response.getWriter();
        int id = 0;
        int gender = 0;
        try {
            id = Integer.parseInt(id_raw);
            gender = Integer.parseInt(gender_raw);
        } catch (NumberFormatException e) {
            System.out.println(e);
        }

        String f_name = request.getParameter("firstname");
        String l_name = request.getParameter("lastname");
        String dob_raw = request.getParameter("dateofbirth");
        Date dob = null;
        if (!"".equals(dob_raw)) {
            dob = Date.valueOf(dob_raw);
        }
        String phone_number = request.getParameter("phone_number");
        String img = "";
        String img_row = request.getParameter("image");
        if (img_row == null || img_row.isEmpty()) {
            img = request.getParameter("oddimg");
        } else {
            img = img_row;
        }
        HttpSession session = request.getSession();
        session.removeAttribute("currentUser");
        UserDAO.INSTANCE.updateUser(id, f_name, l_name, gender, dob, phone_number, img);
        User currentUser = UserDAO.INSTANCE.getUserByUserId(id);
        session.setAttribute("currentUser", currentUser);

//        String updateMessage = "updateprofile";
//        request.setAttribute("process", updateMessage);
//        response.sendRedirect("profile?process=" + URLEncoder.encode(updateMessage, "UTF-8"));
        doGet(request, response);

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

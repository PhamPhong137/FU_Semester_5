/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.StatusDAO;
import dal.TagMemberDAO;
import dal.TagMemberStatusDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import models.Status;
import models.User;

/**
 *
 * @author ACER
 */
public class StatusControllerServlet extends HttpServlet {

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
            out.println("<title>Servlet CreateStatus</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateStatus at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        int type = Integer.parseInt(request.getParameter("type"));
        int id = Integer.parseInt(request.getParameter("id"));
        String page = request.getParameter("page");
        String date_memory_raw = request.getParameter("dateOfMemory");
        Date date_memory = null;
        try {
            if (date_memory_raw != null) {
                date_memory = Date.valueOf(date_memory_raw);
            }
        } catch (Exception e) {
        }
        String tagmemberstatus[] = request.getParameterValues("tagmemberstatus");

        switch (type) {
            case 0:
                String description = request.getParameter("content");
                String place = request.getParameter("place");
                String img;
                String img_raw = request.getParameter("image");
                if ((img_raw == null || img_raw.isEmpty())) {
                    img = request.getParameter("uploadedImage" + id);
                    if (img != null && img.equals("")) { //check th xóa ảnh cũ =>uploadImage =null
                        img = null;
                    }
                } else {
                    img = img_raw;
                }
                TagMemberStatusDAO.INSTANCE.deleteTagStatus(id);
                if (tagmemberstatus != null) {
                    for (int k = 0; k < tagmemberstatus.length; k++) {
                        TagMemberStatusDAO.INSTANCE.addTagStatus(id, Integer.parseInt(tagmemberstatus[k]));
                    }

                }
                StatusDAO.INSTANCE.updateStatus(id, img, description, date_memory, place);
                if (page.equals("status")) {
                    response.sendRedirect("showstatus");
                } else if (page.equals("profile")) {
                    response.sendRedirect("profile");
                } else {
                    response.sendRedirect("familydetail?familyId=" + page);
                }

                break;
            case 1:
                StatusDAO.INSTANCE.deleteStatus(id);
                if (page.equals("status")) {
                    response.sendRedirect("showstatus");
                } else if (page.equals("profile")) {
                    response.sendRedirect("profile");
                } else {
                    response.sendRedirect("familydetail?familyId=" + page);
                }
                break;
            case 2:
                int access = Integer.parseInt(request.getParameter("access"));
                StatusDAO.INSTANCE.updateAudienceEditor(id, access);
                break;
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
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        String page = request.getParameter("page");
        String access_row = request.getParameter("access");
        int access = 0;
        if (access_row != null) {
            access = Integer.parseInt(access_row);
        }

        String place = request.getParameter("place");
        String description = request.getParameter("content");
        String tagmemberstatus[] = request.getParameterValues("tagmemberstatus");
//        String date_memory = request.getParameter("dateOfMemory");
//        PrintWriter out = response.getWriter();
        String date_memory_raw = request.getParameter("dateOfMemory");
        Date date_memory = null;
        try {
            if (date_memory_raw != null) {
                date_memory = Date.valueOf(date_memory_raw);
            }
        } catch (Exception e) {
        }
        String img = null;
        String img_raw = request.getParameter("image");
        if (img_raw != null && !img_raw.isEmpty()) {
            img = img_raw;
        }
        LocalDateTime now = LocalDateTime.now();
        Timestamp date = Timestamp.valueOf(now);
        if (page.equals("status") || page.equals("profile")) {
            int statusid = StatusDAO.INSTANCE.addStatus(currentUser.getUser_id(), img, description, date, date_memory, place, access);
            if (tagmemberstatus != null) {
                for (int k = 0; k < tagmemberstatus.length; k++) {
                    TagMemberStatusDAO.INSTANCE.addTagStatus(statusid, Integer.parseInt(tagmemberstatus[k]));
                }
            }
            if (page.equals("status")) {
                response.sendRedirect("showstatus");
            } else {
                response.sendRedirect("profile");
            }
        } else {
            int family_id = Integer.parseInt(page);
            StatusDAO.INSTANCE.addFamilyStatus(currentUser.getUser_id(), family_id, img, description, date, 1);
            response.sendRedirect("familydetail?familyId=" + page);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import com.google.gson.Gson;
import dal.CalendarDAO;
import dal.FamilyDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import models.Event;
import models.FamilyInfo;
import models.FamilyMember;
import models.UpComingEvent;
import models.User;

/**
 *
 * @author PC-Phong
 */
public class FamilyCalendarServlet extends HttpServlet {

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
            out.println("<title>Servlet FamilyCalendarServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FamilyCalendarServlet at " + request.getContextPath() + "</h1>");
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
        int familyid = Integer.parseInt(request.getParameter("familyid"));

        FamilyInfo family = FamilyDAO.INSTANCE.getFamilyById(familyid);
        FamilyMember familymember = FamilyDAO.INSTANCE.getFamilyMemberById(familyid, currentUser.getUser_id());
        boolean familyExists = FamilyDAO.INSTANCE.checkFamilyExists(familyid, currentUser.getUser_id());
        if (familyExists) {
            List<Event> eventFamilyList = CalendarDAO.INSTANCE.loadEventByFamilyId(familyid);
            List<UpComingEvent> loadFamilyEventUpComing = CalendarDAO.INSTANCE.loadFamilyEventUpComing(familyid);
            Gson gson = new Gson();
            String jsonFamilyEvents = gson.toJson(eventFamilyList);

            request.setAttribute("familymember", familymember);
            request.setAttribute("currentUser", currentUser);
            request.setAttribute("family", family);
            request.setAttribute("eventFamilyList", jsonFamilyEvents);
            request.setAttribute("datenow", java.time.LocalDate.now());
            request.setAttribute("loadFamilyEventUpComing", loadFamilyEventUpComing);
            request.getRequestDispatcher("views/familycalendar.jsp").forward(request, response);
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

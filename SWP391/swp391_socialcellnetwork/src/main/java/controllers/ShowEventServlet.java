/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.AccountDAO;
import dal.CalendarDAO;
import dal.FamilyDAO;
import dal.TagMemberDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

import java.util.List;
import models.EventUser;
import models.User;

/**
 *
 * @author PC-Phong
 */
public class ShowEventServlet extends HttpServlet {

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
            out.println("<title>Servlet ShowEventServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ShowEventServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        String selectedDate = request.getParameter("date");
        List<Integer> countTagMember = new ArrayList<>();

        List<EventUser> loadAllEventsByUserIdAndStartDate = CalendarDAO.INSTANCE.loadAllEventsByUserIdAndStartDate(currentUser.getUser_id(), selectedDate);
        for( EventUser e: loadAllEventsByUserIdAndStartDate){
            countTagMember.add(TagMemberDAO.INSTANCE.countTagMember(e.getEvent_id()));
        }
        request.setAttribute("loadAllEventsByUserIdAndStartDate", loadAllEventsByUserIdAndStartDate);
        request.setAttribute("date", selectedDate);
        request.setAttribute("currentUser", currentUser);
        request.setAttribute("countTag", countTagMember);

        request.getRequestDispatcher("views/showlistevent.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import com.google.gson.Gson;
import dal.CalendarDAO;
import dal.NotifiDAO;
import dal.RemindEventDAO;
import dal.UserDAO;
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
import models.Event;
import models.Notification;
import models.RemindEvent;
import models.UpComingEvent;
import models.User;

/**
 *
 * @author Admin
 */
public class HomeServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet HomeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomeServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LocalDateTime now = LocalDateTime.now();
        Timestamp date = Timestamp.valueOf(now);
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        PrintWriter out = response.getWriter();

        List<Event> loadAllEventByUserId = CalendarDAO.INSTANCE.loadAllEventByUserId(currentUser.getUser_id());

        List<UpComingEvent> eventListComingUp = CalendarDAO.INSTANCE.loadEventUpComing(currentUser.getUser_id());
        List<RemindEvent> showRemindEventByTargetUser = RemindEventDAO.INSTANCE.showRemindEventByTargetUser(currentUser.getUser_id());
        List<User> loadFriendByUserId = UserDAO.INSTANCE.loadFriendByUserId(currentUser.getUser_id());
        List<Notification> notifications = NotifiDAO.INSTANCE.getAllNotificationsForUser(currentUser.getUser_id());
        Gson gson = new Gson();

        String loadAllEventByUserIdJson = gson.toJson(loadAllEventByUserId);

        request.setAttribute("loadAllEventByUserId", loadAllEventByUserIdJson);

        request.setAttribute("eventListComingUp", eventListComingUp);
        request.setAttribute("currentUser", currentUser);
        request.setAttribute("datenow", java.time.LocalDate.now());
        request.setAttribute("loadFriendByUserId", loadFriendByUserId);
        request.setAttribute("dateNow", date);
        session.setAttribute("notifications", notifications);
        session.setAttribute("showRemindEventByTargetUser", showRemindEventByTargetUser);

        String process = request.getParameter("process");
        request.setAttribute("process", process);
        request.getRequestDispatcher("views/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

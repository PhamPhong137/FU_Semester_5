/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.AccountDAO;
import dal.CommentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import models.Comment;
import models.User;

/**
 *
 * @author ACER
 */
public class CommentServlet extends HttpServlet {

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
        String action = request.getParameter("action");
        int statusId = Integer.parseInt(request.getParameter("statusId"));
        if (action.equals("send_data")) {
            String description = request.getParameter("description");
            int userId = Integer.parseInt(request.getParameter("userId"));
            CommentDAO.INSTANCE.addComment(description, statusId, userId);
            User u = AccountDAO.INSTANCE.getUserById(userId);
            int newId = CommentDAO.INSTANCE.getNewCommentId(userId);
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print("<div class=\"commentItem\" id=\"commentItem" + newId + "\">\n"
                    + "  <div class=\"td-img\" rowspan=\"2\"><img src=\"img/" + u.getImage() + "\"></div>\n"
                    + "       <div class=\"commentItem-content\">\n"
                    + "              <span>" + u.getF_name() + " " + u.getL_name() + "</span>\n"
                    + "              <pre id=\"commentItem-content" + newId + "\">" + description + "</pre>\n"
                    + "       </div> \n"
                    + "       <!--drop down edit comment-->                              \n"
                    + "       <div class=\"dropdown dd-comment\" onclick=\"toggleDropdownComment(" + newId + ")\">\n"
                    + "           <i class=\"fas fa-ellipsis-h dropbtn\"></i>\n"
                    + "           <div class=\"dropdown-content\" id=\"dropdownComment" + newId + "\">\n"
                    + "              <a onclick='editComment(" + newId + ")'>Edit</a>\n"
                    + "              <a onclick='showConfirmDeleteComment(" + newId + ")'>Delete</a>\n"
                    + "           </div>\n"
                    + "       </div>  \n"
                    + "  </div> \n"
                    + "</div> \n"
                    + "<div class=\"modal fade\" id=\"popUpToConfirmDeleteComment" + newId + "\" data-bs-backdrop=\"static\" data-bs-keyboard=\"false\" tabindex=\"-1\"\n"
                    + "                             aria-labelledby=\"staticBackdropLabel\" aria-hidden=\"true\">\n"
                    + "     <div class=\"modal-dialog modal-dialog-centered\">\n"
                    + "         <div class=\"modal-content\">\n"
                    + "              <div class=\"modal-header\">\n"
                    + "                 <h1 class=\"modal-title fs-5\" id=\"exampleModalToggleLabel\">Are you sure to delete this comment?</h1>\n"
                    + "              </div>\n"
                    + "              <div class=\"modal-body\" style=\"display: flex; justify-content: space-between;\">\n"
                    + "                   <button type=\"button\" class=\"btn btn-secondary\" class=\"btn-close\" data-bs-dismiss=\"modal\">Cancel</button>\n"
                    + "                   <button type=\"button\" class=\"btn btn-danger\">\n"
                    + "                          <a onclick='deleteComment(" + statusId + "," + newId + ")' data-bs-dismiss=\"modal\" style=\"text-decoration: none; color:white\">\n"
                    + "                               Delete\n"
                    + "                          </a>\n"
                    + "                    </button>\n"
                    + "              </div>\n"
                    + "         </div>\n"
                    + "     </div>\n"
                    + " </div>");
        } else {
            response.setContentType("text/plain");
            String responseData = String.valueOf(CommentDAO.INSTANCE.countComment(statusId));
            response.getWriter().write(responseData);
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        int statusId = Integer.parseInt(request.getParameter("statusId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        CommentDAO.INSTANCE.loadCommentById(statusId);
        List<Comment> commentById = CommentDAO.INSTANCE.getCommentByStatusId(statusId);
        List<User> userOfComment = new ArrayList<>();
        for (Comment c : commentById) {
            userOfComment.add(AccountDAO.INSTANCE.getUserById(c.getUser_id()));
        }
        int index = 0;
        for (Comment c : commentById) {
            out.print("<div class=\"commentItem\" id=\"commentItem" + c.getComment_id() + "\">\n"
                    + "       <div class=\"td-img\" rowspan=\"2\"><img src=\"img/" + userOfComment.get(index).getImage() + "\"></div>\n"
                    + "       <div class=\"commentItem-content\">\n"
                    + "              <span>" + userOfComment.get(index).getF_name() + " " + userOfComment.get(index).getL_name() + "</span>\n"
                    + "              <pre id=\"commentItem-content" + c.getComment_id()+ "\">" + c.getDescription()+ "</pre>\n"
                    + "       </div> \n"
                    + "       <!--drop down edit comment-->                              \n"
                    + "       <div class=\"dropdown dd-comment\" onclick=\"toggleDropdownComment(" + c.getComment_id()+ ")\">\n"
                    + "          <i class=\"fas fa-ellipsis-h dropbtn\"></i>\n");
            if (c.getUser_id() == userId) {
                out.print("     <div class=\"dropdown-content\" id=\"dropdownComment" + c.getComment_id() + "\">\n"
                        + "       <a onclick='editComment(" + c.getComment_id() + ")'>Edit</a>\n"
                        + "       <a onclick='showConfirmDeleteComment(" + c.getComment_id() + ")'>Delete</a>\n"
                        + "     </div>\n");
            }
            out.print("       </div>"
                    + "</div>"
                    + "<div class=\"modal fade\" id=\"popUpToConfirmDeleteComment" + c.getComment_id() + "\" data-bs-backdrop=\"static\" data-bs-keyboard=\"false\" tabindex=\"-1\"\n"
                    + "                             aria-labelledby=\"staticBackdropLabel\" aria-hidden=\"true\">\n"
                    + "                            <div class=\"modal-dialog modal-dialog-centered\">\n"
                    + "                                <div class=\"modal-content\">\n"
                    + "                                    <div class=\"modal-header\">\n"
                    + "                                        <h1 class=\"modal-title fs-5\" id=\"exampleModalToggleLabel\">Are you sure to delete this comment?</h1>\n"
                    + "                                    </div>\n"
                    + "                                    <div class=\"modal-body\" style=\"display: flex; justify-content: space-between;\">\n"
                    + "                                        <button type=\"button\" class=\"btn btn-secondary\" class=\"btn-close\" data-bs-dismiss=\"modal\">Cancel</button>\n"
                    + "                                        <button type=\"button\" class=\"btn btn-danger\">\n"
                    + "                                            <a onclick='deleteComment(" + statusId + "," + c.getComment_id() + ")' data-bs-dismiss=\"modal\" style=\"text-decoration: none; color:white\">\n"
                    + "                                                Delete\n"
                    + "                                            </a>\n"
                    + "                                        </button>\n"
                    + "                                    </div>\n"
                    + "                                </div>\n"
                    + "                            </div>\n"
                    + "                        </div>");
            index++;
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

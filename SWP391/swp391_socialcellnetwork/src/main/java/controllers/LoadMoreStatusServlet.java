/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import dal.AccountDAO;
import dal.CommentDAO;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import models.Status;
import models.User;

/**
 *
 * @author ACER
 */
public class LoadMoreStatusServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet LoadMoreStatusServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoadMoreStatusServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int count = Integer.parseInt(request.getParameter("count"));
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        StatusDAO.INSTANCE.loadStatusTop100(currentUser.getUser_id(),10*count);
        List<Status> showAllStatus = StatusDAO.INSTANCE.getStatusInfo();
        TagMemberStatusDAO t = TagMemberStatusDAO.INSTANCE;
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        int index = 0;
        UserDAO u= UserDAO.INSTANCE;
        for (Status s:showAllStatus){
             out.print("  <div class=\"content-post\">                   \n" +
"                                <div class=\"status-post-1\">\n" +
"                                    <div class=\"status-post-1-header\">\n" +
"                                        <table>\n" +
"                                            <tr>\n" +
"                                                <td class=\"td-img\" rowspan=\"2\"><img src=\"img/"+u.getUserByUserId(s.getUser_id()).getImage()+"\"></td>\n");
                                            if(s.getAccess()==0){
                                            out.print(" <td class=\"td-username\">"+u.getUserByUserId(s.getUser_id()).getF_name()+" "+ u.getUserByUserId(s.getUser_id()).getL_name() +"\n");
                                            if (t.loadTagStatus(s.getStatus_id()).size()>0){
                                            out.print("                        with \n" );
                                             for (Object tu : t.loadTagStatus(s.getStatus_id()) ){
                                                 User tu1= (User) tu;
                                                 out.print(" <span>"+tu1.getF_name()+" "+ tu1.getL_name()+"</span>");
                                             }
                                                 out.print("          </td>\n");
                                            }
                                            }
                                            if(s.getAccess()==1){
out.print(" <td class=\"td-username\">"+u.getUserByUserId(s.getUser_id()).getF_name()+" "+ u.getUserByUserId(s.getUser_id()).getL_name() +"\n");
                                            if (t.loadTagStatus(s.getStatus_id()).size()>0){
                                            out.print("                        with \n" );
                                             for (Object tu : t.loadTagStatus(s.getStatus_id()) ){
                                                 User tu1= (User) tu;
                                                 out.print(" <span>"+tu1.getF_name()+" "+ tu1.getL_name()+"</span>");
                                             }
                                                 out.print("          </td>\n");
                                            }
out.print("                                                        shared to Family\n" +
"                                                    </td>\n" );}
out.print("                                </tr>\n" +
"                                            <tr>\n" +
"                                                <td>"+ s.getDate().toString()+"</td>\n" +
"                                            </tr>\n" +
"                                        </table>\n" +
"                                        <!--drop down edit status -->\n" +
"                                        <div class=\"dropdown\" onclick=\"toggleDropdown("+s.getStatus_id()+")\">\n" +
"                                            <i class=\"fas fa-ellipsis-h dropbtn\"></i>\n" );
if (s.getUser_id() == currentUser.getUser_id()){
out.print("                                                <div class=\"dropdown-content\" id=\"myDropdown"+s.getStatus_id()+"\">\n" +
"                                                    <a onclick =\"showStatusEdit("+s.getStatus_id()+")\">Edit</a>\n" +
"                                                    <a value=\""+s.getStatus_id()+"\" onclick=\"showConfirmDelete("+s.getStatus_id()+")\">Delete</a>\n");
if (s.getAccess()==0) out.print("                                                        <a onclick=\"showEditAudience("+s.getStatus_id()+")\">Edit audience of your status</a>\n" );
out.print("                                                </div>\n");}
out.print("                                  </div>\n" +
"                                    </div>\n" );
if(!s.getPlace().equals("")){
out.print("                                        <span class=\"status-info\"> <strong>Place:</strong> "+s.getPlace()+" </span> <br>\n" );}

if(s.getDate_memory() != null){
out.print("                                    <c:if test=\"${sas.date_memory!= null}\">\n" +
"                                        <span class=\"status-info\"> <strong>Time:  </strong>\n" +
"                                            <fmt:formatDate pattern = \"dd-MM-yyyy\" value = \""+ s.getDate_memory()+"\" />\n" +
"                                        </span>\n" );}
out.print("                                    <pre class=\"post-description\">"+s.getDescription()+"</pre>\n" +
"\n" +
"                                    <div class=\"post-img\">\n");
if (s.getImg()!= null) out.print("  <img src=\"img/"+s.getImg()+"\" alt=\"\">");
out.print("                                        </div>\n" +
"                                        <div class=\"number-of-like-and-comments\">\n" +
"                                            <p class=\"no-padding-margin\" id= \"countLike"+s.getStatus_id()+"\">"+StatusDAO.INSTANCE.countReaction(s.getStatus_id()) + "<i class=\"fas fa-heart\" style=\"color: red;\"></i></p>\n" +
"                                        <p class=\"no-padding-margin\" id=\"countComment"+s.getStatus_id()+"\">"+ CommentDAO.INSTANCE.countComment(s.getStatus_id())+" Comments</p>\n" +
"                                    </div>\n" +
"                                    <hr style=\"margin: 0px 5%;color: rgba(0, 0, 0, 0.5);\">\n" +
"                                    <div class=\"like-comment-saved\">\n" +
"                                        <button class=\"like-btn\" id=\"like"+s.getStatus_id()+"\" onclick=\"toggleLike("+s.getStatus_id()+","+currentUser.getUser_id()+")\">\n" );
if (StatusDAO.INSTANCE.checkReaction(currentUser.getUser_id(), s.getStatus_id()) != -1)
out.print("     <i class=\"fas fa-heart\" style=\"color: red;\"></i>\n" );
else
    out.print("   <i class=\"far fa-heart\"> <span> Like</span></i>\n" );
out.print("                                        </button>\n" +
"                                        <button  class=\"like-btn\" onclick=\"showComment("+s.getStatus_id()+","+currentUser.getUser_id()+")\">\n" +
"                                            <i class=\"far fa-comment\"> <span>Comment</span></i>\n" +
"                                        </button>\n" +
"                                        <button href=\"\" class=\"like-btn\">\n" +
"                                            <i class=\"fas fa-share-square\"></i> <span>Save</span></i>\n" +
"                                        </button>\n" +
"                                    </div>\n" +
"                                    <hr style=\"margin: 0px 5%;color: rgba(0, 0, 0, 0.5);\">\n" +
"                                </div>\n" +
"                            </div>");
             index++;
        }
       
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

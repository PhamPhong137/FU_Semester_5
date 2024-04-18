package com.thinking.machine.tmchat.servlet;
import com.thinking.machine.tmchat.beans.*;
import java.sql.*;
import com.google.gson.*;
import com.thinking.machine.util.*;
import javax.servlet.*; 
import javax.servlet.http.*;
import java.io.*;
public class Logout extends HttpServlet
{
public void doPost(HttpServletRequest request,HttpServletResponse response)
{ 
try
{
PrintWriter pw=response.getWriter();
response.setContentType("text/html");
String responseJson;

HttpSession session=request.getSession();
request.getSession().removeAttribute("member");

JsonObject responseJSONObject=new JsonObject();
responseJSONObject.addProperty("success",true);
responseJson=responseJSONObject.toString();

pw.print(responseJson);
}catch(Exception e)
{
System.out.println(e);
}}}
package com.thinking.machine.tmchat.servlet;
import com.thinking.machine.util.*;
import com.thinking.machine.tmchat.beans.*;
import java.sql.*;
import com.google.gson.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
public class userCreator extends HttpServlet
{
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
try
{
BufferedReader br=request.getReader();
StringBuilder sb=new StringBuilder();
String line;
while(true)
{
line=br.readLine();
if(line==null) break;
sb.append(line);
}
String requestJSON=sb.toString();
Gson gson=new Gson();
UserBean userBean=gson.fromJson(requestJSON,UserBean.class);
String username=userBean.getUsername();
String password=userBean.getPassword();
String name=userBean.getName();
String responseJSON;
response.setContentType("application/json");
PrintWriter pw=response.getWriter();

Class.forName("org.apache.derby.jdbc.ClientDriver");
Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/tmchatdb");
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from member where upper(username)=?");
preparedStatement.setString(1,username.toUpperCase());
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
JsonObject responseJSONObject=new JsonObject();
responseJSONObject.addProperty("success",false);
responseJSONObject.addProperty("exception","Username not available");
responseJSON=responseJSONObject.toString();
pw.print(responseJSON);
return;
}
resultSet.close();
preparedStatement.close();
String passwordKey=java.util.UUID.randomUUID().toString();
passwordKey=passwordKey.replaceAll("-","a");
if(passwordKey.length()>100) passwordKey=passwordKey.substring(0,100);
String encryptedPassword;
encryptedPassword=EncryptionUtility.encrypt(password,passwordKey);
preparedStatement=connection.prepareStatement("insert into member (name,username,e_password,k_password) values (?,?,?,?)");
preparedStatement.setString(1,name);
preparedStatement.setString(2,username);
preparedStatement.setString(3,encryptedPassword);
preparedStatement.setString(4,passwordKey);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
JsonObject responseJSONObject=new JsonObject();
responseJSONObject.addProperty("success",true);
responseJSON=responseJSONObject.toString();
pw.print(responseJSON);
}catch(Exception e)
{
System.out.println(e);// remove after testing
}
}
}
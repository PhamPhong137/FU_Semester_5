package com.thinking.machine.tmchat.servlet;
import com.thinking.machine.tmchat.beans.*;
import java.sql.*;
import com.google.gson.*;
import com.thinking.machine.util.*;
import javax.servlet.*; 
import javax.servlet.http.*;
import java.io.*;
public class Authenticator extends HttpServlet
{
public void doPost(HttpServletRequest request,HttpServletResponse response)
{ 
try
{
PrintWriter pw=response.getWriter();
response.setContentType("text/html");
BufferedReader br=request.getReader();
String line=null;
String jsonString="";
while(true)
{ 
line=br.readLine();
if(line==null) break;
jsonString=jsonString+line;
}
Gson gson=new Gson();
System.out.println(jsonString);
JsonElement jsonElement=gson.fromJson(jsonString,JsonElement.class);
JsonObject jsonObject = jsonElement.getAsJsonObject();
//JsonObject jsonObject=gson.fromJson(jsonString,JsonObject.class);
String username=jsonObject.get("username").getAsString();
String password=jsonObject.get("password").getAsString();
PreparedStatement preparedStatement;
Class.forName("org.apache.derby.jdbc.ClientDriver");
Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/tmchatdb");
preparedStatement=connection.prepareStatement("select * from member where username=?");
preparedStatement.setString(1,username);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
Boolean correct;
correct=resultSet.next();
if(correct)
{
int code=resultSet.getInt("code");
String pp=resultSet.getString("e_password").trim();
String pk=resultSet.getString("k_password").trim();
String decryptedPassword=EncryptionUtility.decrypt(pp,pk);
correct=decryptedPassword.equals(password); 
HttpSession session=request.getSession();
MemberDataStructure mds=new MemberDataStructure(username);
mds.setCode(code);
session.setAttribute("member",mds);
}
resultSet.close();
preparedStatement.close();
connection.close();

String ANS;
if(correct)
{
ANS="{\"success\":true}";
}
 else
{
ANS="{\"success\":false}";
}

System.out.println(ANS);
response.setContentType("application/json");
pw.print(ANS);

 
}catch(Exception exception)
{
System.out.println(exception); // remove after testing
}}}
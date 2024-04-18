package com.thinking.machine.tmchat.servlet;
import com.thinking.machine.tmchat.beans.*;
import java.util.HashSet; 
import com.google.gson.*;
import java.sql.*;
import javax.servlet.*; 
import javax.servlet.http.*;
import java.io.*;
public class usernameAvailabilityVerifier extends HttpServlet
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
System.out.println(jsonObject);

String username= jsonObject.get("username").getAsString();   
String responseJSON;
PreparedStatement preparedStatement;
System.out.println("HYYYYYY");
Class.forName("org.apache.derby.jdbc.ClientDriver");
Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/tmchatdb");
preparedStatement=connection.prepareStatement("select code from member where upper(username)=?");
preparedStatement.setString(1,username.toUpperCase());
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();

 
Boolean available=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
if(available)
{
responseJSON="{\"available\":false}";
}
 else
{
responseJSON="{\"available\":true}";
}

System.out.println(responseJSON);
response.setContentType("application/json");
pw.print(responseJSON);

 
}catch(Exception exception)
{
System.out.println(exception); // remove after testing
}}}
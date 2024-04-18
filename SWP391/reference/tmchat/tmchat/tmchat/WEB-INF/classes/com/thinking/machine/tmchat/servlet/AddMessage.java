package com.thinking.machine.tmchat.servlet;
import java.sql.*;
import static com.thinking.machine.tmchat.servlet.ApplicationSetting.*;
import com.thinking.machine.tmchat.beans.*;
import com.google.gson.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
public class AddMessage extends HttpServlet
{
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
String responseJSON;
try
{
response.setContentType("application/json");
PrintWriter pw=response.getWriter();
/*
MemberDataStructure mds=(MemberDataStructure)request.getSession().getAttribute("member");
if(mds==null)
{
JsonObject responseJSONObject=new JsonObject();
responseJSONObject.addProperty("success",false);
responseJSONObject.addProperty("exception","Invalid credentials");
responseJSON=responseJSONObject.toString();
pw.print(responseJSON);
return;
}
*/
//int fromCode=mds.getCode();
BufferedReader br=request.getReader();
StringBuilder sb=new StringBuilder();
String line;
while(true)
{
line=br.readLine();
if(line==null) break;
sb.append(line);
}
String jsonString=sb.toString();
Gson gson=new Gson();
JsonElement jsonElement=gson.fromJson(jsonString,JsonElement.class);
JsonObject jsonObject = jsonElement.getAsJsonObject();
String toUsername=jsonObject.get("toUsername").getAsString();
String fromUsername=jsonObject.get("fromUsername").getAsString();
String message=jsonObject.get("message").getAsString();
Class.forName("org.apache.derby.jdbc.ClientDriver");
Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/tmchatdb");
PreparedStatement preparedStatement=connection.prepareStatement("select code from member where username=?");
preparedStatement.setString(1,toUsername);
ResultSet resultSet=preparedStatement.executeQuery();
boolean toUsernameCorrect=resultSet.next();

PreparedStatement preparedStatement1=connection.prepareStatement("select code from member where username=?");

preparedStatement1.setString(1,fromUsername);
ResultSet resultSet1;
resultSet1=preparedStatement1.executeQuery();

boolean fromUsernameCorrect=resultSet1.next();


if(toUsernameCorrect==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
JsonObject responseJSONObject=new JsonObject();
responseJSONObject.addProperty("success",false);
responseJSONObject.addProperty("exception","Invalid request");
responseJSON=responseJSONObject.toString();
pw.print(responseJSON);
return;
}
int fromCode=resultSet1.getInt("code");
int toCode=resultSet.getInt("code");

resultSet.close();
preparedStatement.close();
java.util.Date now=new java.util.Date();
java.sql.Date sqlDate=new java.sql.Date(now.getYear(),now.getMonth(),now.getDate());
java.sql.Time sqlTime=new java.sql.Time(now.getHours(),now.getMinutes(),now.getSeconds());
preparedStatement=connection.prepareStatement("insert into message (message_date,message_time,from_code,to_code,message,status)values(?,?,?,?,?,'N')",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setDate(1,sqlDate);
preparedStatement.setTime(2,sqlTime);
preparedStatement.setInt(3,fromCode);
preparedStatement.setInt(4,toCode);
preparedStatement.setString(5,message);
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
long messageCode=resultSet.getLong(1);
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("insert into notification(notification_date,notification_time,member_code,entity_code,notification_type) values (?,?,?,?,?)");
preparedStatement.setDate(1,sqlDate);
preparedStatement.setTime(2,sqlTime);
preparedStatement.setInt(3,toCode);
preparedStatement.setLong(4,messageCode);
preparedStatement.setInt(5,MESSAGE_NOTIFICATION); // 4 for message notification
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
JsonObject responseJSONObject=new JsonObject();
responseJSONObject.addProperty("success",true);
responseJSON=responseJSONObject.toString();
pw.print(responseJSON);
return;
}catch(Exception e)
{
System.out.println(e);// remove after testing
}
}
}
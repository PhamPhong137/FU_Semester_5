package com.thinking.machine.tmchat.servlet;
import static com.thinking.machine.tmchat.servlet.ApplicationSetting.*;
import com.thinking.machine.tmchat.beans.*;
import com.google.gson.*;
import com.thinking.machine.util.*;
import javax.servlet.*; 
import java.sql.*;
import javax.servlet.http.*;
import java.io.*;
public class removeFriend extends HttpServlet
{
public void doPost(HttpServletRequest request,HttpServletResponse response)
{ 
try
{
PrintWriter pw=response.getWriter();
response.setContentType("application/json");
String responseJson="";




MemberDataStructure mds=(MemberDataStructure)request.getSession().getAttribute("member");
if(mds==null)
{
JsonObject responseJsonObject=new JsonObject();
responseJsonObject.addProperty("success",false);
responseJsonObject.addProperty("Exception","Wrong Username Session");
responseJson=responseJsonObject.toString();
pw.print(responseJson);
System.out.println(responseJson);
return;
}



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
HttpSession Session=request.getSession();
String fromUsername=mds.getUsername();
System.out.println(fromUsername);
//String fromUsername=jsonObject.get("fromUsername").getAsString();
String toUsername=jsonObject.get("toUsername").getAsString();
//int code=mds.getCode();
//int code=jsonObject.get("code");
PreparedStatement preparedStatement;
Class.forName("org.apache.derby.jdbc.ClientDriver");
Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/tmchatdb");
preparedStatement=connection.prepareStatement("select code from member where username=?");

preparedStatement.setString(1,toUsername);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();

boolean toUsernameCorrect=resultSet.next();

System.out.println("4");
PreparedStatement preparedStatement1=connection.prepareStatement("select code from member where username=?");

preparedStatement1.setString(1,fromUsername);
ResultSet resultSet1;
resultSet1=preparedStatement1.executeQuery();

boolean fromUsernameCorrect=resultSet1.next();



System.out.println("1");

if(toUsernameCorrect)
{
int code=resultSet1.getInt("code");
int toUsernameCode=resultSet.getInt("code");
preparedStatement.close();
resultSet.close();
preparedStatement=connection.prepareStatement("select * from friend where ((friend_code=? and member_code=?) or (friend_code=? and member_code=?))");
preparedStatement.setInt(1,toUsernameCode);
preparedStatement.setInt(2,code);
preparedStatement.setInt(3,code);
preparedStatement.setInt(4,toUsernameCode);
resultSet=preparedStatement.executeQuery();
boolean correct1=resultSet.next();
if(correct1)
{
preparedStatement.close();
resultSet.close();
System.out.println("43");
preparedStatement=connection.prepareStatement("delete from friend where ((friend_code=? and member_code=?) or (friend_code=? and member_code=?))");
preparedStatement.setInt(1,toUsernameCode);
preparedStatement.setInt(2,code);
preparedStatement.setInt(3,code);
preparedStatement.setInt(4,toUsernameCode);
preparedStatement.executeUpdate();







preparedStatement=connection.prepareStatement("insert into notification(notification_date,notification_time,member_code,entity_code,notification_type) values (?,?,?,?,?)");
java.util.Date utilDate=new java.util.Date();
java.sql.Date sqlDate=new java.sql.Date(utilDate.getYear(),utilDate.getMonth(),utilDate.getDate());
java.sql.Time sqlTime=new
java.sql.Time(utilDate.getHours(),utilDate.getMinutes(),utilDate.getSeconds());
preparedStatement.setDate(1,sqlDate);
preparedStatement.setTime(2,sqlTime);
preparedStatement.setInt(3,toUsernameCode);
preparedStatement.setLong(4,code);
preparedStatement.setInt(5,UNFRIENDED_NOTIFICATION); // 3 for friend request
preparedStatement.executeUpdate();
preparedStatement.close();







}
else
{
resultSet.close();
preparedStatement.close();
JsonObject responseJsonObject=new JsonObject();
responseJsonObject.addProperty("success",false);
responseJsonObject.addProperty("REMARK","YOU ARE not FRIENDS ");
responseJson=responseJsonObject.toString();
pw.print(responseJson);
return;
}
}
System.out.println("hn");
resultSet.close();
preparedStatement.close();
connection.close();
if(toUsernameCorrect)
{
responseJson="{\"success\":true}";
}
else
{
//responseJson="{\"success\":false}";
resultSet.close();
preparedStatement.close();
JsonObject responseJsonObject=new JsonObject();
responseJsonObject.addProperty("success",false);
responseJsonObject.addProperty("Exception","Invalid friend request");
responseJson=responseJsonObject.toString();
pw.print(responseJson);
return;
}

System.out.println(responseJson);
response.setContentType("application/json");
pw.print(responseJson);

 
}catch(Exception exception)
{
System.out.println(exception); // remove after testing
}}}
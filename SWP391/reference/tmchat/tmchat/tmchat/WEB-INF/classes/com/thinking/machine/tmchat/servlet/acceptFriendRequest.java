package com.thinking.machine.tmchat.servlet;
import com.thinking.machine.tmchat.beans.*;
import static com.thinking.machine.tmchat.servlet.ApplicationSetting.*;
import com.google.gson.*;
import com.thinking.machine.util.*;
import javax.servlet.*; 
import java.sql.*;
import javax.servlet.http.*;
import java.io.*;
public class acceptFriendRequest extends HttpServlet
{
public void doPost(HttpServletRequest request,HttpServletResponse response)
{ 
try
{
PrintWriter pw=response.getWriter();
response.setContentType("application/json");


/*

MemberDataStructure mds=(MemberDataStructure)request.getSession().getAttribute("member");
if(mds==null)
{
JsonObject responseJsonObject=new JsonObject();
responseJsonObject.addProperty("success",false);
responseJsonObject.addProperty("Exception","Wrong Username Session");
responseJson=responseJsonObject.toString();
pw.print(responseJson);
return;
}*/



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
String responseJson;
JsonElement jsonElement=gson.fromJson(jsonString,JsonElement.class);
JsonObject jsonObject = jsonElement.getAsJsonObject();
HttpSession Session=request.getSession();
//String fromUsername=mds.getUsername();
//System.out.println(fromUsername);
System.out.println("HNNN");
String requestSenderUsername=jsonObject.get("requestSenderUsername").getAsString();
String requestAccepterUsername=jsonObject.get("requestAccepterUsername").getAsString();
//int code=mds.getCode();
System.out.println("43");
PreparedStatement preparedStatement;
Class.forName("org.apache.derby.jdbc.ClientDriver");
Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/tmchatdb");
preparedStatement=connection.prepareStatement("select code from member where username=?");

preparedStatement.setString(1,requestSenderUsername);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();

boolean requestSenderUsernameCorrect=resultSet.next();



PreparedStatement preparedStatement1=connection.prepareStatement("select code from member where username=?");

preparedStatement1.setString(1,requestAccepterUsername);
ResultSet resultSet1;
resultSet1=preparedStatement1.executeQuery();

boolean requestAccepterUsernameCorrect=resultSet1.next();
System.out.println("1");


if(requestSenderUsernameCorrect)
{
int code=resultSet1.getInt("code");
int requestSenderUsernameCode=resultSet.getInt("code");
System.out.println(code+","+requestSenderUsernameCode);
preparedStatement.close();
resultSet.close();
preparedStatement1.close();
resultSet1.close();
System.out.println("43");
preparedStatement=connection.prepareStatement("select * from friend where ((friend_code=? and member_code=?) or (friend_code=? and member_code=?))");
preparedStatement.setInt(1,requestSenderUsernameCode);
preparedStatement.setInt(2,code);
preparedStatement.setInt(3,code);
preparedStatement.setInt(4,requestSenderUsernameCode);

resultSet=preparedStatement.executeQuery();
boolean correct=resultSet.next();
//System.out.println("3434");


if(!(correct))
{
System.out.println("hyyyyyyy");
preparedStatement.close();
resultSet.close();
preparedStatement1.close();
resultSet1.close();
preparedStatement=connection.prepareStatement("select * from friend_request where (sent_by=? and sent_to=?)");
preparedStatement.setInt(1,requestSenderUsernameCode);
preparedStatement.setInt(2,code);
//preparedStatement.setInt(3,code);
//preparedStatement.setInt(4,requestSenderUsernameCode);

resultSet=preparedStatement.executeQuery();
boolean correct1=resultSet.next();

System.out.println("kya hua");

preparedStatement1=connection.prepareStatement("select * from friend_request where (sent_by=? and sent_to=?)");
preparedStatement1.setInt(1,requestSenderUsernameCode);
preparedStatement1.setInt(2,code);

resultSet1=preparedStatement1.executeQuery();
System.out.println("kyaaaaa");
if(resultSet1.next())
{
System.out.println("chala ");
preparedStatement.close();
resultSet.close();
preparedStatement1.close();
resultSet1.close();

preparedStatement=connection.prepareStatement("insert into friend (friend_code,member_code) values(?,?)");
preparedStatement.setInt(1,requestSenderUsernameCode);
preparedStatement.setInt(2,code);

preparedStatement1=connection.prepareStatement("delete from friend_request where sent_by=? and sent_to=?");
preparedStatement1.setInt(1,requestSenderUsernameCode);
preparedStatement1.setInt(2,code);
/*preparedStatement1.setInt(1,code);
preparedStatement1.setInt(2,requestSenderUsernameCode);*/
preparedStatement1.executeUpdate();
preparedStatement.executeUpdate();
System.out.println("35fdg42");








preparedStatement=connection.prepareStatement("insert into notification(notification_date,notification_time,member_code,entity_code,notification_type) values (?,?,?,?,?)"); 
java.util.Date utilDate=new java.util.Date(); 
java.sql.Date sqlDate=new java.sql.Date(utilDate.getYear(),utilDate.getMonth(),utilDate.getDate()); 
java.sql.Time sqlTime=new java.sql.Time(utilDate.getHours(),utilDate.getMinutes(),utilDate.getSeconds()); 
preparedStatement.setDate(1,sqlDate); 
preparedStatement.setTime(2,sqlTime);
 preparedStatement.setInt(3,requestSenderUsernameCode); 
preparedStatement.setLong(4,code); 
preparedStatement.setInt(5,FRIEND_REQUEST_ACCEPTED_NOTIFICATION); // 2 for friend request 
preparedStatement.executeUpdate(); 
preparedStatement.close();






System.out.println("35wqer42");
}
else
{
resultSet.close();
preparedStatement.close();
JsonObject responseJsonObject=new JsonObject();
responseJsonObject.addProperty("success",false);
responseJsonObject.addProperty("REMARK","THEIR IS NO REQUEST FROM THIS USER");
responseJson=responseJsonObject.toString();
pw.print(responseJson);
System.out.println(responseJson);
return;
}
}
else
{
resultSet.close();
preparedStatement.close();
JsonObject responseJsonObject=new JsonObject();
responseJsonObject.addProperty("success",true);
responseJsonObject.addProperty("exception","ALREADY FRIENDS");
responseJson=responseJsonObject.toString();
pw.print(responseJson);
System.out.println(responseJson);
return;
}
}
resultSet.close();
preparedStatement.close();






connection.close();
if(requestSenderUsernameCorrect)
{
responseJson="{\"success\":true}";
System.out.println(responseJson);
}
else
{
responseJson="{\"success\":false}";
resultSet.close();
preparedStatement.close();
JsonObject responseJsonObject=new JsonObject();
responseJsonObject.addProperty("success",false);
responseJsonObject.addProperty("Exception","Invalid USER");
responseJson=responseJsonObject.toString();
pw.print(responseJson);
System.out.println(responseJson);
return;
}
System.out.println(responseJson);
response.setContentType("application/json");
pw.print(responseJson);
}catch(Exception exception)
{
System.out.println(exception); // remove after testing
}}}
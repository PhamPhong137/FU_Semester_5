package com.thinking.machine.tmchat.servlet;
import java.sql.*;
import static com.thinking.machine.tmchat.servlet.ApplicationSetting.*;
import com.thinking.machine.util.*;
import com.thinking.machine.tmchat.beans.*;
import com.google.gson.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
public class CloseAccount extends HttpServlet
{
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
String responseJSON;
try
{
response.setContentType("application/json");
PrintWriter pw=response.getWriter();
/*MemberDataStructure mds=(MemberDataStructure)request.getSession().getAttribute("member");
if(mds==null)
{
JsonObject responseJSONObject=new JsonObject();
responseJSONObject.addProperty("success",false);
responseJSONObject.addProperty("exception","Invalid credentials");
responseJSON=responseJSONObject.toString();
pw.print(responseJSON);
return;
}*/
//int code=mds.getCode();
//String username=mds.getUsername();
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
String username=jsonObject.get("username").getAsString();
String password=jsonObject.get("password").getAsString();
Class.forName("org.apache.derby.jdbc.ClientDriver");
Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/tmchatdb");
PreparedStatement preparedStatement1=connection.prepareStatement("select code from member where username=?");

preparedStatement1.setString(1,username);
ResultSet resultSet1;
resultSet1=preparedStatement1.executeQuery();

boolean fromUsernameCorrect=resultSet1.next();

int code=resultSet1.getInt("code");
System.out.println("hy");



PreparedStatement preparedStatement=connection.prepareStatement("select * from member where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet=preparedStatement.executeQuery();
boolean correct;
correct=resultSet.next();
System.out.println("hello");
if(!correct)
{
System.out.println("hello0o");
resultSet.close();
preparedStatement.close();
connection.close();
JsonObject responseJSONObject=new JsonObject();
responseJSONObject.addProperty("success",false);
responseJSONObject.addProperty("exception","Invalid credentials");
responseJSON=responseJSONObject.toString();
pw.print(responseJSON);
System.out.println(responseJSON);
return;
}
String encryptedPassword=resultSet.getString("e_password").trim();
String passwordKey=resultSet.getString("k_password").trim();
resultSet.close();
preparedStatement.close();
String decryptedPassword=EncryptionUtility.decrypt(encryptedPassword,passwordKey);
correct=password.equals(decryptedPassword);
System.out.println("hello0000000");
if(!correct)
{
System.out.println("hello......");
connection.close();
JsonObject responseJSONObject=new JsonObject();
responseJSONObject.addProperty("success",false);
responseJSONObject.addProperty("exception","Invalid credentials");
responseJSON=responseJSONObject.toString();
pw.print(responseJSON);
System.out.println(responseJSON);
return;
}

preparedStatement=connection.prepareStatement("insert into closed_account values(?)");
preparedStatement.setInt(1,code);
preparedStatement.executeUpdate();
preparedStatement.close();
System.out.println("hellookjoijp");
preparedStatement=connection.prepareStatement("delete from friend where member_code=? or friend_code=?");
preparedStatement.setInt(1,code);
preparedStatement.setInt(2,code);
preparedStatement.executeUpdate();
preparedStatement.close();
System.out.println("hellookjoijp");
preparedStatement=connection.prepareStatement("delete from friend_request where sent_by=? or sent_to=?");
preparedStatement.setInt(1,code);
preparedStatement.setInt(2,code);
preparedStatement.executeUpdate();
preparedStatement.close();
System.out.println("hellookjoijp");
preparedStatement=connection.prepareStatement("delete from notification where member_code=?");
preparedStatement.setInt(1,code);
preparedStatement.executeUpdate();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from notification where entity_code=? and notification_type not in (?)");
preparedStatement.setInt(1,code);
preparedStatement.setInt(2,MESSAGE_NOTIFICATION);
preparedStatement.executeUpdate();
preparedStatement.close();
//HttpSession session=request.getSession();
//request.getSession().removeAttribute("member");
preparedStatement=connection.prepareStatement("delete from member where code= ? ");
preparedStatement.setInt(1,code);
preparedStatement.executeUpdate();
preparedStatement.close();
RequestDispatcher rs=request.getRequestDispatcher("//index.jsp");
rs.forward(request,response);
JsonObject responseJSONObject=new JsonObject();
responseJSONObject.addProperty("success",true);
responseJSON=responseJSONObject.toString();
pw.print(responseJSON);
System.out.println(responseJSON);
return;
}catch(Exception e)
{
System.out.println(e);// remove after testing
}
}
}
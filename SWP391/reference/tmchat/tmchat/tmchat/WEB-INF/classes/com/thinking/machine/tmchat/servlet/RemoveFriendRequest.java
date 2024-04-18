package com.thinking.machine.tmchat.servlet;
import java.sql.*;
import static com.thinking.machine.tmchat.servlet.ApplicationSetting.*;
import com.thinking.machine.tmchat.beans.*;
import com.google.gson.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
public class RemoveFriendRequest extends HttpServlet
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
}
int code=mds.getCode();
String username=mds.getUsername();
*/
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
String fromUsername=jsonObject.get("fromUsername").getAsString();
String toUsername=jsonObject.get("toUsername").getAsString();
Class.forName("org.apache.derby.jdbc.ClientDriver");
Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/tmchatdb");
PreparedStatement preparedStatement=connection.prepareStatement("select code from member where username=?");
preparedStatement.setString(1,fromUsername);
ResultSet resultSet=preparedStatement.executeQuery();
boolean toUsernameCorrect=resultSet.next();

PreparedStatement preparedStatement1=connection.prepareStatement("select code from member where username=?");

preparedStatement1.setString(1,toUsername);
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
int fromUsernameCode=resultSet.getInt("code");
int code=resultSet1.getInt("code");
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select * from friend_request where sent_to=? and sent_by=?");
preparedStatement.setInt(1,code);
preparedStatement.setInt(2,fromUsernameCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
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
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from friend_request where sent_to=? and sent_by=?");
preparedStatement.setInt(1,code);
preparedStatement.setInt(2,fromUsernameCode);
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
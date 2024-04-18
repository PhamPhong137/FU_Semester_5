package com.thinking.machine.tmchat.servlet;
import com.thinking.machine.tmchat.beans.*;
import java.sql.*;
import com.google.gson.*;
import com.thinking.machine.util.*;
import javax.servlet.*; 
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
public class FriendRequest extends HttpServlet
{
public void doPost(HttpServletRequest request,HttpServletResponse response)
{ 
try
{
PrintWriter pw=response.getWriter();
response.setContentType("text/html");
String responseJson;

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
String MemberCode=jsonObject.get("Member_code").getAsString();
PreparedStatement preparedStatement;
Class.forName("org.apache.derby.jdbc.ClientDriver");
Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/tmchatdb");
preparedStatement=connection.prepareStatement("select username from member where code in (select sent_by from friend_request where sent_to=?)");

preparedStatement.setString(1,MemberCode);

ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
JSONArray json = new JSONArray();
ResultSetMetaData metadata = resultSet.getMetaData();
int numColumns = metadata.getColumnCount();
while(resultSet.next())
{
JSONObject obj = new JSONObject(); 
for (int i = 1; i <= numColumns; ++i) 
{
String column_name = metadata.getColumnName(i);
obj.put(column_name, resultSet.getObject(column_name));
}
json.add(obj);
}
response.setContentType("application/json");
jsonString = json.toJSONString();
System.out.println("jsonString:" + jsonString);
pw.print(jsonString);
}catch(Exception exception)
{
System.out.println(exception); // remove after testing
}
}
}
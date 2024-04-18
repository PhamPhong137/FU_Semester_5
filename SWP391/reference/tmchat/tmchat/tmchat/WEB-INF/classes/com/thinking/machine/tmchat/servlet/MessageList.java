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
import java.text.DateFormat;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
import org.json.simple.JSONObject;
public class MessageList extends HttpServlet
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
int MemberCode=jsonObject.get("Member_code").getAsInt();
PreparedStatement preparedStatement;
Class.forName("org.apache.derby.jdbc.ClientDriver");
Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/tmchatdb");
preparedStatement=connection.prepareStatement("select mem.username,M.message from member mem,message M where M.from_code=mem.code AND M.to_code=?");

//select mem.name,M.message,M.message_date,M.message_time from message M,member mem where M.from_code=mem.code AND M.to_code=1
preparedStatement.setInt(1,MemberCode);
ResultSet resultSet;

resultSet=preparedStatement.executeQuery();
/*
Date date=resultSet.getDate("message_date");
System.out.println("hyyyyy");
DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
String strDate = dateFormat.format(date);  

System.out.println(strDate);
*/
JSONArray json = new JSONArray();
ResultSetMetaData metadata = resultSet.getMetaData();
int numColumns = metadata.getColumnCount();
System.out.println(numColumns);
while(resultSet.next())
{
JSONObject obj = new JSONObject(); 
for (int i = 1; i <= numColumns; ++i) 
{
String column_name = metadata.getColumnName(i);
obj.put(column_name, resultSet.getObject(column_name));
System.out.println(column_name);
System.out.println(obj);
}
json.add(obj);
}
response.setContentType("application/json");
jsonString = json.toJSONString();
System.out.println( jsonString);
pw.print(jsonString);
}catch(Exception exception)
{
System.out.println(exception); // remove after testing
}
}
}
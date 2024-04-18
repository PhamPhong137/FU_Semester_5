package com.thinking.machine.tmchat.servlet;
import com.thinking.machine.tmchat.beans.*;
import javax.servlet.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.servlet.http.*;
import java.io.*;
public class UserAvatar extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
try
{
String defaultAvatar="default.jpg";
String separator=File.separator;
String avatarFolder=getServletContext().getRealPath("/");
avatarFolder=avatarFolder+"WEB-INF"+separator+"avatars"+separator;
String avatarToDispatch;
MemberDataStructure mds=(MemberDataStructure)request.getSession().getAttribute("member");
if(mds==null)
{
avatarToDispatch=avatarFolder+defaultAvatar;
}
else
{
String userAvatar="__"+mds.getUsername()+"__.jpg";
avatarToDispatch=avatarFolder+userAvatar;
File file=new File(avatarToDispatch);
if(file.exists()==false)
{
avatarToDispatch=avatarFolder+defaultAvatar;
}
}


BufferedImage bi = ImageIO.read(new File(avatarToDispatch));
		OutputStream out = response.getOutputStream();
response.setContentType("image/jpeg");
		ImageIO.write(bi, "jpg", out);
		out.close();









/*FileInputStream fileInputStream=new FileInputStream(new File(avatarToDispatch));
ServletOutputStream outputStream;
outputStream=response.getOutputStream();
response.setContentType("image/jpeg");
int i;
while(true)
{
i=fileInputStream.read();
if(i==-1) break;
outputStream.write(i);
}
System.out. println("YES");
fileInputStream.close();*/
}catch(Exception e)
{
System.out.println(e);
}
}
}
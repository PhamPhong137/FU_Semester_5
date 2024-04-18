package com.thinking.machine.tmchat.beans;
import java.io.*;
public class UserBean implements Serializable
{
private String username;
private String password;
private String name;
private int code;
public UserBean()
{
this.username="";
this.password="";
this.name="";
this.code=0;
}
public void setUsername(String username)
{
this.username=username;
}
public String getUsername()
{
return this.username;
}
public void setPassword(String password)
{
this.password=password;
}
public String getPassword()
{
return this.password;
}
public void setName(String name)
{
this.name=name;
}
public String getName()
{
return this.name;
}
public void setCode(int code)
{
this.code=code;
}
public int getCode()
{
return this.code;
}
}
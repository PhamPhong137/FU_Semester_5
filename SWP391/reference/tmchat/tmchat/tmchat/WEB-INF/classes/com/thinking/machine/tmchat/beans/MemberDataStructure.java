package com.thinking.machine.tmchat.beans;
import java.io.*;
public class MemberDataStructure implements Serializable
{
private String username;
private int code;
public MemberDataStructure(String username)
{
this.username=username;
}
public void setCode(int code)
{
this.code=code;
}
public int getCode()
{
return this.code;
}
public String getUsername()
{
return this.username;
}
}
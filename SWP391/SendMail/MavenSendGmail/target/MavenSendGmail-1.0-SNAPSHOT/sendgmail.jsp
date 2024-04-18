<%-- 
    Document   : sendgmail
    Created on : Jan 31, 2024, 6:31:04 PM
    Author     : PC-Phong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>

    <form action="sendmail" method="post">
        <div class="form-group">
            <label for="name">email</label>
            <input type="text"  name="email">
        </div>
        
        <button type="submit">submit</button>
    </form>
</html>

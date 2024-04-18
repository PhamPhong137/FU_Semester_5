<%-- 
    Document   : index
    Created on : Jan 11, 2024, 3:44:49 AM
    Author     : PC-Phong
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
    <style media="screen" type="text/css">
        .chat {
            width: 70%;
            height: 200px;
            border: 1px solid silver;
            overflow-y: scroll;
        }
        #msg {
            width: 99%;
        }
        
    </style>
    <script type="text/javascript">
        console.log("test");
        var wsUrl;
        if (window.location.protocol === 'http:') {
            wsUrl = 'ws://';
        } else {
            wsUrl = 'wss://';
        }
        console.log("test2");
        var ws = new WebSocket(wsUrl + window.location.host + '<%= request.getContextPath() %>/chat');

        ws.onmessage = function (event) {
            var mySpan = document.getElementById("chat");
            mySpan.innerHTML += event.data + "<br/>";
        };

        ws.onerror = function (event) {
            console.log("Error ", event);
        };

        function sendMsg() {
            var msg = document.getElementById("msg").value;
            if (msg) {
                ws.send(msg);
            }
            document.getElementById("msg").value = "";
        }
    </script>
</head>
<body>
    <h1 style="padding-left:300px">Live Chat EZ</h1>

    <div>
        <div id="chat" class="chat"></div>
        <div>
            <input style="width:150px" type="text"  name="msg" id="msg" placeholder="Enter message here"/>
            <button onclick="return sendMsg();">Enter</button>
        </div>
    </div>
</body>
</html>

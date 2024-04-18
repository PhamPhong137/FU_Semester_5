
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author PC-Phong
 */
@ServerEndpoint("/chat")
public class ChatServlet {
    
    private static final Set<Session> userSessions = Collections.newSetFromMap(new ConcurrentHashMap<Session, Boolean>());

    @OnOpen
    public void onOpen(Session curSession)
    {
        userSessions.add(curSession);
    }
            
    @OnClose
    public void onClose(Session curSession)
    {
        userSessions.remove(curSession);
    }
    
    @OnMessage
    public void onMessage(String message, Session userSession)
    {
        for(Session ses : userSessions)
        {
            ses.getAsyncRemote().sendText(message);
        }
    }

}

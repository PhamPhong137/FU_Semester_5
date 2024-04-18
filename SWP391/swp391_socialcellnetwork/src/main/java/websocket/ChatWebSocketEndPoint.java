package websocket;
import models.MessageModel;
import jakarta.websocket.EncodeException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.ConversationMessage;

@ServerEndpoint(value = "/chat/{conversationId}", decoders = MessageModelDecoder.class, encoders = MessageModelEncoder.class)
public class ChatWebSocketEndPoint {

    private static Map<String, Set<Session>> Conversations = Collections.synchronizedMap(new HashMap<>());

    @OnOpen
    public void onOpen(Session session, @PathParam("conversationId") String conversationId) {
        Conversations.computeIfAbsent(conversationId, k -> Collections.synchronizedSet(new HashSet<>())).add(session);
    }

    @OnMessage
    public void onMessage(Session session, MessageModel message, @PathParam("conversationId") String conversationId) {
        System.out.println("Handling message in conversation " + conversationId + ": " + message);
        Set<Session> sessionsInConversation = Conversations.get(conversationId);
        if (sessionsInConversation != null) {
            for (Session s : sessionsInConversation) {
                try {
                    s.getBasicRemote().sendObject(message);
                } catch (IOException | EncodeException ex) {
                    Logger.getLogger(ChatWebSocketEndPoint.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("conversationId") String conversationId) {
        System.out.println("On close: " + session.getId() + " in conversation " + conversationId);
        Set<Session> sessionsInConversation = Conversations.get(conversationId);
        if (sessionsInConversation != null) {
            sessionsInConversation.remove(session);
            if (sessionsInConversation.isEmpty()) {
                Conversations.remove(conversationId);
            }
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package websocket;

import com.google.gson.Gson;
import models.MessageModel;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;
import models.ConversationMessage;

/**
 *
 * @author Fpt
 */
public class MessageModelEncoder implements Encoder.Text<MessageModel>{

    Gson gson = new Gson();
    
    @Override
    public String encode(MessageModel message) throws EncodeException {
        return gson.toJson(message);
    }

    @Override
    public void init(EndpointConfig config) {
        
    }

    @Override
    public void destroy() {
        
    }
    
}

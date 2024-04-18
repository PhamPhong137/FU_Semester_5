 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package websocket;

import com.google.gson.Gson;
import models.MessageModel;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;
import models.ConversationMessage;

/**
 *
 * @author Fpt
 */
public class MessageModelDecoder implements Decoder.Text<MessageModel>{
    
    Gson gson = new Gson();
    
    @Override
    public MessageModel decode(String s) throws DecodeException {
        return gson.fromJson(s, MessageModel.class);
    }

    @Override
    public boolean willDecode(String s) {
        return s != null;
    }

    @Override
    public void init(EndpointConfig config) {
        
    }

    @Override
    public void destroy() {
        
    }
    
}
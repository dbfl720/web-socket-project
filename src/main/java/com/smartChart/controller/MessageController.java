package com.smartChart.controller;


import com.smartChart.model.MessageModel;
import com.smartChart.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;


// 들어오는 메세지, 보내는 메세지를 관리하는 controller
@RestController
public class MessageController {


    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{to}")  // chat은 websocketConfiguration에서 registry.addEndpoint("/chat") 여기있는 chat임. // to 누구에게 보낼 것인가.
    public void sendMessage(@DestinationVariable String to, MessageModel message) {
        System.out.println("handling sned message: " + message + " to:" + to);
        boolean isExists = UserStorage.getInstance().getUsers().contains(to);
        if (isExists) {
            simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);  // topic은 websocketConfiguration에서 enableSimpleBroker("/topic"); 이것.
        }
    }
}

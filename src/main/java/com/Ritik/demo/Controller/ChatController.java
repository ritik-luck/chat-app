package com.Ritik.demo.Controller;

import com.Ritik.demo.Model.ChatMessage;
import com.Ritik.demo.Services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat/{roomId}/sendMessage")
    @SendTo("/topic/chat/{roomId}")
    public ChatMessage sendMessage(@DestinationVariable String roomId, @Payload ChatMessage chatMessage) {
        logger.info("Received message for room {}: {}", roomId, chatMessage);
        chatMessage.setRoomId(roomId);
        ChatMessage savedMessage = chatService.saveMessage(chatMessage);
        logger.info("Message saved and being sent to room {}: {}", roomId, savedMessage);
        return savedMessage;
    }

    @MessageMapping("/chat/{roomId}/addUser")
    @SendTo("/topic/chat/{roomId}")
    public ChatMessage addUser(@DestinationVariable String roomId, @Payload ChatMessage chatMessage, 
                             SimpMessageHeaderAccessor headerAccessor) {
        logger.info("User joining room {}: {}", roomId, chatMessage);
        chatMessage.setRoomId(roomId);
        chatMessage.setMessageType(ChatMessage.MessageType.JOIN);
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        headerAccessor.getSessionAttributes().put("roomId", roomId);
        ChatMessage savedMessage = chatService.saveMessage(chatMessage);
        logger.info("Join message saved and being sent to room {}: {}", roomId, savedMessage);
        return savedMessage;
    }
}
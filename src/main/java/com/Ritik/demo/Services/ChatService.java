package com.Ritik.demo.Services;

import com.Ritik.demo.Model.ChatMessage;
import com.Ritik.demo.Repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
    
    @Autowired
    private ChatMessageRepository repo;

    public ChatMessage saveMessage(ChatMessage message){
        logger.info("Attempting to save message: {}", message);
        message.setTimestamp(LocalDateTime.now());
        try {
            ChatMessage savedMessage = repo.save(message);
            logger.info("Message saved successfully with ID: {}", savedMessage.getId());
            return savedMessage;
        } catch (Exception e) {
            logger.error("Error saving message: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<ChatMessage> getChatHistory(String roomId) {
        logger.info("Fetching chat history for room: {}", roomId);
        return repo.findByRoomIdOrderByTimestampAsc(roomId);
    }
}

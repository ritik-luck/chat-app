package com.Ritik.demo.Controller;

import com.Ritik.demo.Model.ChatMessage;
import com.Ritik.demo.Services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatHistoryController {
    @Autowired
    private ChatService service;


    @GetMapping("/history/{roomId}")
    public ResponseEntity<List<ChatMessage>> getChatHistory(@PathVariable String roomId) {
        return ResponseEntity.ok(service.getChatHistory(roomId));
    }
}

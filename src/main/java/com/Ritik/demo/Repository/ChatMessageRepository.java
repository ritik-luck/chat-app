package com.Ritik.demo.Repository;

import com.Ritik.demo.Model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    List<ChatMessage> findByRoomIdOrderByTimestampAsc(String roomId);
}

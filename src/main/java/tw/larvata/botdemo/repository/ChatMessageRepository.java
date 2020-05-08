package tw.larvata.botdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.larvata.botdemo.model.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}

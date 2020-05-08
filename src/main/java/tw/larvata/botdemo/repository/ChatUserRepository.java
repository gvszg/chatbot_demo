package tw.larvata.botdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.larvata.botdemo.model.ChatUser;

import java.util.Optional;

public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {
    Optional<ChatUser> findByTelegramId(long telegramId);
}

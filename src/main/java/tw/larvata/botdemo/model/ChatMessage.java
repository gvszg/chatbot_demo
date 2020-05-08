package tw.larvata.botdemo.model;

import com.google.inject.internal.cglib.reflect.$FastClass;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_message", indexes = {
        @Index(columnList = "chat_user_id", name = "chat_user_id_in_message")
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_user_id", nullable = false)
    private Long chatUserId;

    @Column(name = "receive_msg")
    private String receiveMsg;

    @Column(name = "receive_at")
    private LocalDateTime receiveAt;

    @Column(name = "send_msg")
    private String sendMsg;

    @Column(name = "send_at")
    private LocalDateTime sendAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

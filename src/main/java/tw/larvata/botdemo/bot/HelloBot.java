package tw.larvata.botdemo.bot;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import tw.larvata.botdemo.model.ChatMessage;
import tw.larvata.botdemo.model.ChatUser;
import tw.larvata.botdemo.repository.ChatMessageRepository;
import tw.larvata.botdemo.repository.ChatUserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component
public class HelloBot extends TelegramLongPollingBot {
    @Value("${telegram.bot.username}")
    private String username;
    @Value("${telegram.bot.token}")
    private String token;
    @Autowired
    private ChatUserRepository chatUserRepository;
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = null;
        User user = null;
        Long chatId = null;
        String text = null;
        ChatUser chatUser = null;
        ChatMessage receiveChatMessage = null;

        message = update.getMessage();
        user = message.getFrom();
        chatId = message.getChatId();  // telegram id
        text = message.getText();

        Optional<ChatUser> chatUserOptional = chatUserRepository.findByTelegramId(chatId);
        if (chatUserOptional.isPresent()) {
            chatUser = chatUserOptional.get();
            log.info("find chat user: {}", chatUser.toString());
        } else {
            // save chat user to db
            chatUser = ChatUser.builder()
                    .telegramId(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .userName(user.getUserName())
                    .build();
            chatUserRepository.save(chatUser);
            log.info("save chat user to db: {}", chatUser.toString());
        }

        // save chat message to db
        receiveChatMessage = ChatMessage.builder()
        .chatUserId(chatUser.getId())
        .receiveMsg(text)
        .receiveAt(LocalDateTime.now())
        .build();
        chatMessageRepository.save(receiveChatMessage);
        log.info("save chat message to db: {}", receiveChatMessage.toString());

        this.sendHelloMessage(chatId, chatUser.getId());
    }

    public void sendHelloMessage(long chatId, Long chatUserId) throws TelegramApiException {
        String text = "Larvata!";
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);

        ChatMessage sendChatMessage = ChatMessage.builder()
                .chatUserId(chatUserId)
                .sendMsg(text)
                .sendAt(LocalDateTime.now())
                .build();
        chatMessageRepository.save(sendChatMessage);
        log.info("Bot response: {}", sendMessage.toString());
        this.execute(sendMessage);

        String stickerId = "CAACAgUAAxUAAV60ucXauhT9zn9uWdg07yQRide1AAJSAAOxXRkUHa8WUgl8Ry4ZBA";
        SendSticker sendSticker = new SendSticker();
        sendSticker.setChatId(chatId);
        sendSticker.setSticker(stickerId);

        ChatMessage stickerMessage = ChatMessage.builder()
                .chatUserId(chatUserId)
                .sendMsg(stickerId)
                .sendAt(LocalDateTime.now())
                .build();
        chatMessageRepository.save(stickerMessage);
        log.info("Bot send a sticker: {}", sendSticker.toString());
        this.execute(sendSticker);
    }

    @Override
    public String getBotUsername() {
        return this.username;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }
}

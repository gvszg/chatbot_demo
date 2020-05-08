package tw.larvata.botdemo.bot;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class HelloBot extends TelegramLongPollingBot {
    @Value("${telegram.bot.username}")
    private String username;
    @Value("${telegram.bot.token}")
    private String token;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = null;
        User chatUser = null;
        Long chatId = null;
        String text = null;
        Chat chat = null;

        message = update.getMessage();

        log.info("Message: {}", message.toString());

        chatUser = message.getFrom();
        log.info("Chat User: {}", chatUser.toString());

        chatId = message.getChatId();
        log.info("Chat Id: {}", chatId.toString());

        chat = message.getChat();
        log.info("Chat: {}", chat.toString());

        text = message.getText();
        log.info("Text: {}", text);

        this.sendHelloMessage(chatId);
    }

    public void sendHelloMessage(long chatId) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Larvata!");

        log.info("Bot response: {}", sendMessage.toString());
        this.execute(sendMessage);
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

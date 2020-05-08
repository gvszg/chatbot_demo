package tw.larvata.botdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import tw.larvata.botdemo.bot.HelloBot;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@SpringBootApplication
public class BotdemoApplication implements CommandLineRunner {
	@Value("${telegram.bot.username}")
	private static String username;
	@Value("${telegram.bot.token}")
	private static String token;
	@Autowired
    private DataSource dataSource;

	public static void main(String[] args) {
	    ApiContextInitializer.init();
		SpringApplication.run(BotdemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		showConnection();
	}

	private void showConnection() throws SQLException {
		log.info("DataSource: {}", dataSource.toString());
		Connection connection = dataSource.getConnection();
		log.info("Connection: {}", connection);
		connection.close();
	}
}

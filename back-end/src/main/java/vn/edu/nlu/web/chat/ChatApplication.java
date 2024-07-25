package vn.edu.nlu.web.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Configuration
@EnableJpaAuditing
public class ChatApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ChatApplication.class, args);

	}


}
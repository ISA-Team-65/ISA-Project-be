package com.team65.isaproject;

import com.rabbitmq.client.AMQP;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
@EnableMethodSecurity
@EnableTransactionManagement
public class IsaprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsaprojectApplication.class, args);
	}

	@EventListener({ApplicationReadyEvent.class})
	void applicationReadyEvent() {
		System.out.println("Application started ... launching browser now");
		browse("http://localhost:8080/swagger-ui/index.html");
	}

	public static void browse(String url) {
		if(Desktop.isDesktopSupported()){
			Desktop desktop = Desktop.getDesktop();
			try {
				desktop.browse(new URI(url));
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}else{
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

	@Bean
	Queue queue() { return new Queue("delivery", true); }
	@Bean
	public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
	}
}

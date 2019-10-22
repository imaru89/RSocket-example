package com.dev.life.rsocket.producer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.time.Instant;

@SpringBootApplication
public class ProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

}

@Controller
class GreetingsRSocketController {

	@MessageMapping("greet")
	GreetingsResponse greet(GreetingsRequest request){
		return new GreetingsResponse(request.getName());
	}

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class GreetingsRequest {
	private String name;
}

@Data
@NoArgsConstructor
class GreetingsResponse {
	private String greeting;

	public GreetingsResponse(String name){
		this.greeting = "Hello " + name + ". The time is: " + Instant.now();
	}

}


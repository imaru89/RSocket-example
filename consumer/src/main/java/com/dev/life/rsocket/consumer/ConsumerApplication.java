package com.dev.life.rsocket.consumer;

import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.reactivestreams.Publisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

	@Bean
	public RSocket rSocket(){
		return RSocketFactory
			.connect()
			.dataMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE)
			.frameDecoder(PayloadDecoder.ZERO_COPY)
			.transport(TcpClientTransport.create(7000))
			.start()
			.block();
	}

	@Bean
	public RSocketRequester requester(RSocketStrategies rSocketStrategies){
		return RSocketRequester.builder().rsocketFactory(clientRSocketFactory -> clientRSocketFactory
				.dataMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE)
				.frameDecoder(PayloadDecoder.ZERO_COPY))
			.rsocketStrategies(rSocketStrategies)
			.connect(TcpClientTransport.create(7000))
			.retry()
			.block();
	}

	@RestController
	class GreetingsRestController {
		private final RSocketRequester requester;

		public GreetingsRestController(RSocketRequester requester) {
			this.requester = requester;
		}

		@GetMapping("/greet/{name}")
		Publisher<GreetingsResponse> greet(@PathVariable String name){
			return this.requester
				.route("greet")
				.data(new GreetingsRequest(name))
				.retrieveMono(GreetingsResponse.class);
		}
	}
}

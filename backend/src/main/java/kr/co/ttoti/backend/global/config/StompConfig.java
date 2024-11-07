package kr.co.ttoti.backend.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSocketMessageBroker
@Configuration
public class StompConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/chat") // STOMP endpoint 설정
			.setAllowedOriginPatterns("*")
			.withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/sub"); // /sub를 구독하고 있는 모든 클라이언트에게 메세지를 보냄
		registry.setApplicationDestinationPrefixes("/pub"); // 클라이언트가 /pub로 메세지를 보내면
	}

	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
		registry.setMessageSizeLimit(160 * 64 * 1024);
		registry.setSendTimeLimit(100 * 10000);
		registry.setSendBufferSizeLimit(3 * 512 * 1024);
	}

}

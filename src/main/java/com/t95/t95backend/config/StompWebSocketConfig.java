package com.t95.t95backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeHandler;

import com.t95.t95backend.utils.encryption.JwtTokenUtils;

@Configuration
@EnableWebSocketMessageBroker 
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	private final JwtTokenUtils jwtTokenUtils;
	
	 @Autowired
	    public StompWebSocketConfig(JwtTokenUtils jwtTokenUtils) {
	        this.jwtTokenUtils = jwtTokenUtils;
	    }
	
	@Override
	public void configureMessageBroker(final MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic", "/user", "/subscribe");
		registry.setApplicationDestinationPrefixes("/globalIndex");
	}
	
	@Override
	public void registerStompEndpoints(final StompEndpointRegistry registry) {
		registry.addEndpoint("/t95-websocket").setAllowedOrigins("*").withSockJS();
		registry.addEndpoint("/t95-websocket").setAllowedOrigins("*");
	}
	
	 @SuppressWarnings("deprecation")
	@Override
	    public void configureClientInboundChannel(ChannelRegistration registration) {
	        registration.setInterceptors(new AuthChannelInterceptor(jwtTokenUtils));
	    }
}

package com.t95.t95backend.config;

import java.util.Objects;

import javax.security.auth.message.AuthException;

import com.sun.security.auth.UserPrincipal;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.t95.t95backend.utils.encryption.JwtTokenUtils;

public class AuthChannelInterceptor implements ChannelInterceptor {

	private final Logger LOG = LoggerFactory.getLogger(DefaultHandshakeHandler.class);
    private final JwtTokenUtils jwtTokenUtils;

    public AuthChannelInterceptor(JwtTokenUtils jwtTokenUtils) {
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        assert accessor != null;
        String jwt = null;
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            try {
                jwt = accessor.getNativeHeader("Authorization").get(0);
            } catch (Exception e) {
                throw new RuntimeException("some header can't find...");
            }
            if (jwt != null) {
                try {
                    jwtTokenUtils.getJwtInfo(jwt);
                } catch (AuthException e) {
                    // TODO: handle error by self
                	System.out.println("UnAuthorized JWT for Websocket connection");
                    throw new RuntimeException("UnAuthorization");
                }
                String sessionId = accessor.getSessionId();
                accessor.setUser(new UserPrincipal(Objects.requireNonNull(sessionId)));
//                LOG.info("User with ID '{}' opened the page", sessionId);
            }
        }
        return message;
    }
}

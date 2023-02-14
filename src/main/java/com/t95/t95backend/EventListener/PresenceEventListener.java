package com.t95.t95backend.EventListener;

import javax.security.auth.message.AuthException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.t95.t95backend.utils.encryption.JwtTokenUtils;

@Component
public class PresenceEventListener implements ApplicationListener<ApplicationEvent> {
	
	private WebSocketSessions webSocketSessions;	
//	private SimpMessagingTemplate messagingTemplate;	
	private final JwtTokenUtils jwtTokenUtils;
	private final Logger logger = LoggerFactory.getLogger(getClass());
    
	@Autowired	
	public PresenceEventListener(WebSocketSessions webSocketSessions, JwtTokenUtils jwtTokenUtils) {
		this.webSocketSessions = webSocketSessions;
		this.jwtTokenUtils = jwtTokenUtils;
	}
	
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if(event instanceof SessionConnectEvent) {
			handleSessionConnected((SessionConnectEvent) event);
		} else if(event instanceof SessionDisconnectEvent) {
			handleSessionDisconnect((SessionDisconnectEvent) event);
		}
	}

	private void handleSessionConnected(SessionConnectEvent event) {
		StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
        
        String user;
        
        String jwt = headers.getNativeHeader("Authorization").get(0);
        try {
            user = jwtTokenUtils.getJwtInfo(jwt).getName();
//            messagingTemplate.convertAndSendToUser(sessionId, loginDestination, loginEvent);
    		webSocketSessions.addSessionId(headers.getSessionId(), user);
    		logger.info("user login, user:{}, sessionId: {}", user, headers.getSessionId());
        } catch (AuthException e) {
            throw new RuntimeException(e);
        }
	}

	private void handleSessionDisconnect(SessionDisconnectEvent event) {
		String sessionId = StompHeaderAccessor.wrap(event.getMessage()).getSessionId();
		webSocketSessions.removeSessionId(sessionId);
		logger.info("user logout, sessionId: {}", sessionId);
	}
}
package com.t95.t95backend.EventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class WebSocketSessions {
	
	private final ConcurrentHashMap<String, String> sessionUsers = new ConcurrentHashMap<>();
	
	public synchronized void addSessionId(String sessionId, String User) {
		if(sessionId != null && User != null) sessionUsers.put(sessionId, User);
	}
	
	public synchronized void removeSessionId(String sessionId) {
		if(sessionId != null) sessionUsers.remove(sessionId);
	}
	
	public List<String> getAllUsers() {
		return new ArrayList<>(sessionUsers.values());
	}
	
	public List<String> getAllSessionIds() {
		return new ArrayList<>(sessionUsers.keySet());
	}
	
	public List<String> getSessionIdsByUser(String user) {
		ArrayList<String> sessionIds = new ArrayList<>();
		sessionUsers.forEach((key, userInMap) -> {
			if(userInMap.equals(user)) sessionIds.add(key);
		});
		return sessionIds;
	}

}

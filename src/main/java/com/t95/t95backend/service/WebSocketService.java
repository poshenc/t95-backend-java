//package com.t95.t95backend.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class WebSocketService {
//	
//    private static final String BROADCAST_DESTINATION = "/topic/messages";
//    private static final String USER_TOPIC = "/marketData";
//
//    private final SimpMessagingTemplate template;
//
//    @Autowired
//    public WebSocketService(SimpMessagingTemplate template) {
//        this.template = template;
//    }
//
//    private void sendToDestination(String destination, Object message) {
//        template.convertAndSend(destination, message);
//    }
//
//    private void sendToSession(String sessionId, Object message) {
//        template.convertAndSendToUser(sessionId, USER_TOPIC, message);
//    }
//
//    private void sendToUser(String user, Object message) {
//        sessions.getSessionIdsByUser(user).forEach(sessionId -> template.convertAndSendToUser(sessionId, USER_TOPIC, message));
//    }
//
//    public void notify(Object message) {
//        sendToDestination(BROADCAST_DESTINATION, message);
//    }
//
//    public void notify(String user, Object message) {
//        this.sendToUser(user, message);
//    }
//}

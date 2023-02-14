package com.t95.t95backend.config;

import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.nio.file.AccessDeniedException;

@Component
public class AuthStompSubProtocolErrorHandler extends StompSubProtocolErrorHandler {
    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable ex) {
        Throwable exception = ex;
        if (exception instanceof MessageDeliveryException) {
            exception = exception.getCause();
        }

        if (exception instanceof RuntimeException) {
            return handleUnauthorizedException(clientMessage, exception, HttpStatus.UNAUTHORIZED);
        }

        if (exception instanceof AccessDeniedException) {
            return handleUnauthorizedException(clientMessage, exception, HttpStatus.FORBIDDEN);
        }

        return super.handleClientMessageProcessingError(clientMessage, ex);
    }

    private Message<byte[]> handleUnauthorizedException(Message<byte[]> clientMessage, Throwable ex, HttpStatus errorCode) {
        String message = "Unauthorized TOKEN";

        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);

        accessor.setMessage(String.valueOf(errorCode));

        accessor.setLeaveMutable(true);

        return MessageBuilder.createMessage(message.getBytes(), accessor.getMessageHeaders());
    }
}

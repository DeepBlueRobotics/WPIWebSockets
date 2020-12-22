package com.asyncapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageHandlerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandlerService.class);

    
      
    /**
     * General channel for WPILib WebSocket messages
     */
    public void handleWpilibwsPublish(Message<?> message) {
        LOGGER.info("handler wpilibws");
        LOGGER.info(String.valueOf(message.getPayload().toString()));
    }
      
    

}

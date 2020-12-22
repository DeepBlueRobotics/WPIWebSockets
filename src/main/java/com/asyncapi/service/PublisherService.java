package com.asyncapi.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface PublisherService {

    
        
    /**
     * General channel for WPILib WebSocket messages
     */
    @Gateway(requestChannel = "wpilibwsSubscribeOutboundChannel")
    void wpilibwsSubscribe(String data);
        
    
}

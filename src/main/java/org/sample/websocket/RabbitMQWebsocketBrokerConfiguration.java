package org.sample.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class RabbitMQWebsocketBrokerConfiguration extends AbstractWebSocketMessageBrokerConfigurer {
   
    
    private static final String STOMP_TOPIC = "/topic";
    private static final String STOMP_EXCHANGE = "/exchange";
    private static final String APP_PREFIX = "/app";
    private static final String EXCHANGE_PREFIX = "/exchange";
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        
        LOGGER.debug("Setting up Websocket Broker");
                
        // Ensure we setup a relay for /exchange client (targeted user)
        config.enableStompBrokerRelay(STOMP_TOPIC, STOMP_EXCHANGE)
        .setRelayHost("localhost")
        .setClientLogin("brokeruser")
        .setClientPasscode("Password1")
        .setSystemLogin("brokeruser")
        .setSystemPasscode("Password1")
        .setVirtualHost("echo_ws_vhost")
        .setRelayPort(61613)
        .setUserRegistryBroadcast("/topic/simp-user-registry");
        
        /**
         * TODO : The following needs uncommented once Spring boot 1.3
         * is available.
         */
        //.setUserRegistryBroadcast("/topic/simp-user-registry");
              
        config.setApplicationDestinationPrefixes(APP_PREFIX, EXCHANGE_PREFIX);
        //config.enableSimpleBroker(STOMP_TOPIC, STOMP_EXCHANGE);
    }

    /**
     * JS Clients register at this stomp endpoint, it is an authenticated 
     * endpoint.
     * 
     * @param registry  The stomp registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        
        // Even with SockJS support, IE9 client connection needs 
        // to be forced to used iFrame mechanism. JS clients connect to this endpoint
        registry.addEndpoint("/websocket").withSockJS();
    }
}


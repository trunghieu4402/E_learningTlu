package com.example.ElearningTLU.Config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
//@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketConfigurer {
    private final JwtDecoder jwtDecoder;

    public WebSocketConfig(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(totorial(), "/totorial")
                .setAllowedOrigins("*");
    }
    @Bean
    WebSocketHandler totorial()
    {
        return  new TotorialHander(jwtDecoder);
    }
//    private  WebSocketHandshakeInterceptor webSocketHandshakeInterceptor= new WebSocketHandshakeInterceptor();
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.enableSimpleBroker("/topic"); // Đây là prefix cho các topic
//        config.setApplicationDestinationPrefixes("/app"); // Prefix cho các endpoint
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//
//        registry.addEndpoint("/totorial")
////                .addInterceptors(this.webSocketHandshakeInterceptor)  // Đăng ký interceptor
////                .setAllowedOrigins("*")
//                .withSockJS();
//    }
}
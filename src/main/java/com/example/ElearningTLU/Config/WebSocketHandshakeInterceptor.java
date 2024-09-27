package com.example.ElearningTLU.Config;

import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component
@RequiredArgsConstructor

public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandshakeInterceptor.class);
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // Lấy token từ header Authorization
        System.out.println("Vao day");
        logger.info("New WebSocket Handshake: " + request.getURI());
        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            System.out.println("Đ Nhan Tokent");
            String jwtToken = authHeader.substring(7);  // Loại bỏ 'Bearer ' để lấy token

            // Thực hiện kiểm tra token
            if (validateToken(jwtToken)) {
                attributes.put("user", getUserFromToken(jwtToken));  // Lưu thông tin user
                return true;  // Token hợp lệ, cho phép kết nối
            }
        }

        return false;  // Từ chối kết nối nếu token không hợp lệ
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception ex) {
        // Logic sau khi handshake (nếu cần)
    }

    private boolean validateToken(String token) {
        // Logic kiểm tra token ở đây (ví dụ, dùng JWT library để verify token)
        return true;
    }

    private String getUserFromToken(String token) {
        var auth= SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(auth.getAuthorities());
        System.out.println(auth.getName()
        );
        return auth.getName();
    }
}

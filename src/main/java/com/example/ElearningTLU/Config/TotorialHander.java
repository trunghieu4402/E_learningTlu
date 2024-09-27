package com.example.ElearningTLU.Config;

import com.example.ElearningTLU.Services.RegisterService.RegisterServiceImpl;
import com.example.ElearningTLU.Utils.JWTToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class TotorialHander extends TextWebSocketHandler {
    private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final JwtDecoder jwtDecoder;

    public TotorialHander(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }
    @Autowired
    private RegisterServiceImpl registerService;
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Nhận tin nhắn từ client
        String clientMessage = message.getPayload();
        System.out.println("Received message: " + clientMessage);

        // Lấy headers từ WebSocketSession
        Map<String, List<String>> headers = session.getHandshakeHeaders();

        // Lấy token từ header Authorization
        List<String> authHeaders = headers.get("Authorization");
        if (authHeaders != null && !authHeaders.isEmpty()) {
            String token = authHeaders.get(0).replace("Bearer ", "");

            try {
                // Decode và validate token bằng JwtDecoder
                Jwt decodedToken = jwtDecoder.decode(token);


                // Sử dụng thông tin từ token (ví dụ: user id, roles)
                String userId = decodedToken.getSubject();
              ResponseEntity<?> a = this.registerService.register(userId,clientMessage);
//                System.out.println("User ID: " +userId);

                // Tiếp tục xử lý tin nhắn sau khi xác thực token (nếu cần)
                for(WebSocketSession s :sessions)
                {
                    s.sendMessage(new TextMessage(a.toString()));
                }


            } catch (JwtException e) {
                // Xử lý token không hợp lệ
                System.out.println("Invalid JWT: " + e.getMessage());
                session.sendMessage(new TextMessage("Invalid token!"));
                session.close(); // Đóng kết nối nếu cần
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}

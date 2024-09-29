package com.example.ElearningTLU.Controllers.Student;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RealTimeController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    @MessageMapping("/register")
    @SendTo("/topic/classUpdates")
    public String register(@Payload Message mess)
    {
        System.out.println(mess.getMessage());
//        simpMessagingTemplate.convertAndSendToUser(mess.get,"/topic", mess);
        return "hihi";
    }
}

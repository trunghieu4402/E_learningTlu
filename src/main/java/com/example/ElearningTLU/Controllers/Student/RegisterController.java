package com.example.ElearningTLU.Controllers.Student;

import com.example.ElearningTLU.Utils.JWTToken;
import com.example.ElearningTLU.Services.RegisterService.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("student/register")
public class RegisterController {
    @Autowired
    private RegisterService registerService;
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private JWTToken token;
@GetMapping("checkRegisterTime")
public boolean checkRegisterTime()
{
    String person = this.token.getUser();
    return this.registerService.checkRegisterTime(person);
}
//    @MessageMapping("/register")
//    @SendTo("/topic/ classUpdates")
//    public ResponseEntity<?> register(@RequestParam("id") String classroomId)
//    {
//        String person = this.token.getUser();
//        return this.registerService.register(person,classroomId);
//    }
    @MessageMapping("/hi")
    @SendTo("/topic/updates")
    public String chat()
    {

        return "Chao";
    }
    @DeleteMapping("/remove")
    public  ResponseEntity<?> remove(@RequestParam("id") String classRoomId)
    {
        String person= this.token.getUser();
        return this.registerService.removeClassRoom(person,classRoomId);

    }

    @GetMapping("")
    public ResponseEntity<?> getAll()
    {
        String perId= this.token.getUser();
        return this.registerService.getAllCLass(perId);
    }

    @GetMapping("/getRegister")
    public ResponseEntity<?> getPreSchedule()
    {
        String userId  = this.token.getUser();
        return this.registerService.getPreSchedule(userId);
    }

}

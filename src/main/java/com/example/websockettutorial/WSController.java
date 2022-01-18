package com.example.websockettutorial;

import com.example.websockettutorial.dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WSController {

    @Autowired
    private WSService wsService;

    @PostMapping("/send-message")
    public void sendMessage(@RequestBody final Message message){
        wsService.notifyFrontend(message.getMessageContent());
    }
}

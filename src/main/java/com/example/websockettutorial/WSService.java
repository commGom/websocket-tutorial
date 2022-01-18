package com.example.websockettutorial;

import com.example.websockettutorial.dto.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WSService {
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public WSService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void notifyFrontend(final String message){
        ResponseMessage response = new ResponseMessage(message);

        simpMessagingTemplate.convertAndSend("/topic/messages",response);
    }
}

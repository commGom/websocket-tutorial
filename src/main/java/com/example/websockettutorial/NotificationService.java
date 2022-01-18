package com.example.websockettutorial;

import com.example.websockettutorial.dto.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public NotificationService(SimpMessagingTemplate messagingTemplate){
        this.simpMessagingTemplate = messagingTemplate;
    }

    public void sendGlobalNotification(){
        ResponseMessage message = new ResponseMessage("Global Notification");

        simpMessagingTemplate.convertAndSend("/topic/global-notifications", message);
    }

    public void sendPrivateNotification(final String userId){
        ResponseMessage message = new ResponseMessage("Global Notification");

        simpMessagingTemplate.convertAndSendToUser(userId,"/topic/private-notifications", message);
    }
}

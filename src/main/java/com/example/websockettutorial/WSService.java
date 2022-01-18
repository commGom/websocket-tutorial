package com.example.websockettutorial;

import com.example.websockettutorial.dto.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WSService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NotificationService notificationService;

    @Autowired
    public WSService(SimpMessagingTemplate simpMessagingTemplate, NotificationService notificationService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.notificationService = notificationService;
    }

    public void notifyFrontend(final String message){
        ResponseMessage response = new ResponseMessage(message);
        notificationService.sendGlobalNotification();
        simpMessagingTemplate.convertAndSend("/topic/messages",response);
    }

    public void notifyUser(final String id, final String message){
        ResponseMessage response = new ResponseMessage(message);
        notificationService.sendPrivateNotification(id);
        simpMessagingTemplate.convertAndSendToUser(id,"/topic/private-messages",response);
    }
}

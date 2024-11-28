package com.example.backend.controller;

import com.example.backend.model.Message;

import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

@RestController
@AllArgsConstructor
public class ChatController {

    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@RequestBody Message message) throws InterruptedException {
        System.out.println(message);
        return message;
    }
    @MessageMapping("/private-message")
    public Message privateMessage(@RequestBody Message message){
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        return message;
    }

}
//public class ChatController {
//
//    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
//    private SimpMessagingTemplate simpMessagingTemplate;
//
//    @MessageMapping("/message")
//    @SendTo("/chatroom/public")
//    public Message receiveMessage(@RequestBody Message message) throws InterruptedException {
//        try {
//            // Add MDC context
//            MDC.put("username", message.getSenderName());
//            MDC.put("messageType", "public");
//
//            logger.info("Received public message: {}", message);
//            System.out.println(message);
//            return message;
//        } finally {
//            MDC.clear();
//        }
//    }
//
//    @MessageMapping("/private-message")
//    public Message privateMessage(@RequestBody Message message) {
//        try {
//            // Add MDC context
//            MDC.put("username", message.getSenderName());
//            MDC.put("messageType", "private");
//
//            logger.info("Received private message for {}: {}", message.getReceiverName(), message);
//            simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message);
//            System.out.println(message);
//            return message;
//        } finally {
//            MDC.clear();
//        }
//    }
//}
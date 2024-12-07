package com.example.backend.controller;

import com.example.backend.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ChatControllerTest {

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;

    @InjectMocks
    private ChatController chatController;

    @Test
    void testReceiveMessage() throws InterruptedException {
        // Arrange
        Message message = new Message();
        message.setSenderName("John");
        message.setMessage("Hello, everyone!");

        // Act
        Message returnedMessage = chatController.receiveMessage(message);

        // Assert
        // Ensure the returned message matches the input
        assertEquals("John", returnedMessage.getSenderName());
        assertEquals("Hello, everyone!", returnedMessage.getMessage());
    }

    @Test
    void testPrivateMessage() {
        // Arrange
        Message message = new Message();
        message.setSenderName("John");
        message.setReceiverName("Jane");
        message.setMessage("Hello, Jane!");

        // Act
        chatController.privateMessage(message);

        // Assert
        // Verify that the private message is sent to the correct user
        verify(simpMessagingTemplate, times(1))
                .convertAndSendToUser(eq("Jane"), eq("/private"), eq(message));
    }
}

//
//import com.example.backend.model.Message;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.messaging.converter.MappingJackson2MessageConverter;
//import org.springframework.messaging.simp.stomp.StompSession;
//import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
//import org.springframework.messaging.simp.stomp.StompSessionHandler;
//import org.springframework.messaging.simp.stomp.StompHeaders;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.web.socket.WebSocketHttpHeaders;
//import org.springframework.web.socket.client.standard.StandardWebSocketClient;
//import org.springframework.web.socket.messaging.WebSocketStompClient;
//
//import java.lang.reflect.Type;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.LinkedBlockingDeque;
//import java.util.concurrent.TimeUnit;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class WebSocketTest {
//
//    private static final String WEBSOCKET_URI = "ws://localhost:8081/ws"; // Replace with your actual URI
//    private static final String TOPIC_PUBLIC = "/chatroom/public";
//
//    private BlockingQueue<Message> blockingQueue;
//    private WebSocketStompClient stompClient;
//
//    @BeforeEach
//    void setup() {
//        blockingQueue = new LinkedBlockingDeque<>();
//        stompClient = new WebSocketStompClient(new StandardWebSocketClient());
//        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
//    }
//
//    @Test
//    void testSendMessage() throws Exception {
//        // Connect to the WebSocket
//        StompSession session = stompClient.connect(WEBSOCKET_URI, new WebSocketHttpHeaders(), new TestSessionHandler()).get(1, TimeUnit.SECONDS);
//
//        // Subscribe to a topic
//        session.subscribe(TOPIC_PUBLIC, new TestStompFrameHandler());
//
//        // Send a message
//        Message message = new Message();
//        message.setSenderName("John");
//        message.setMessage("Hello, everyone!");
//        session.send("/app/message", message);
//
//        // Wait and verify the response
//        Message receivedMessage = blockingQueue.poll(5, TimeUnit.SECONDS);
//        assertThat(receivedMessage).isNotNull();
//        assertThat(receivedMessage.getSenderName()).isEqualTo("John");
//        assertThat(receivedMessage.getMessage()).isEqualTo("Hello, everyone!");
//    }
//
//    private class TestStompFrameHandler implements org.springframework.messaging.simp.stomp.StompFrameHandler {
//        @Override
//        public Type getPayloadType(StompHeaders headers) {
//            return Message.class;
//        }
//
//        @Override
//        public void handleFrame(StompHeaders headers, Object payload) {
//            blockingQueue.offer((Message) payload);
//        }
//    }
//
//    private static class TestSessionHandler extends StompSessionHandlerAdapter {
//        @Override
//        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
//            System.out.println("Connected to WebSocket server!");
//        }
//    }
//}

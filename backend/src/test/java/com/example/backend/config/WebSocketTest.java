package com.example.backend.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WebSocketConfigTest {

    @InjectMocks
    private WebSocketConfig webSocketConfig;

    @Mock
    private StompEndpointRegistry stompEndpointRegistry;

    @Mock
    private StompWebSocketEndpointRegistration stompWebSocketEndpointRegistration;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterStompEndpoints() {
        // Mock the behavior of addEndpoint() to return a mocked StompWebSocketEndpointRegistration
        when(stompEndpointRegistry.addEndpoint("/ws")).thenReturn(stompWebSocketEndpointRegistration);

        // Mock the behavior of setAllowedOriginPatterns
        when(stompWebSocketEndpointRegistration.setAllowedOriginPatterns("*")).thenReturn(stompWebSocketEndpointRegistration);

        // Call the method to test
        webSocketConfig.registerStompEndpoints(stompEndpointRegistry);

        // Verify the interactions with the mocked objects
        verify(stompEndpointRegistry).addEndpoint("/ws");
        verify(stompWebSocketEndpointRegistration).setAllowedOriginPatterns("*");
    }
}
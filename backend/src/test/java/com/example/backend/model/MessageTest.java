package com.example.backend.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.Assert;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageTest {

    @Test
    void testMessageCreation() {
        Message message = new Message();
        message.setMessage("Hello");
        message.setSenderName("Alice");
        message.setReceiverName("Bob");

        Assert.assertEquals("Hello", message.getMessage());
        Assert.assertEquals("Alice", message.getSenderName());
        Assert.assertEquals("Bob", message.getReceiverName());
    }
}


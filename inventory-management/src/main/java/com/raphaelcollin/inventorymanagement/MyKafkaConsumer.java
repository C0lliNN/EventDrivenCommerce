package com.raphaelcollin.inventorymanagement;

import org.springframework.stereotype.Component;
import org.springframework.kafka.annotation.KafkaListener;

@Component
public class MyKafkaConsumer {

    @KafkaListener(topics = "example-topic", groupId = "group_id")
    public void consume(String message) {
        System.out.println("message = " + message);
    }
}
package com.raphaelcollin.inventorymanagement;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MyKafkaConsumer {

    @KafkaListener(topics = "example-topic", groupId = "group_id")
    public void consume(String message) {
        System.out.println("message = " + message);
    }
}
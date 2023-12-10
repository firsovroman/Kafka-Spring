package com.example.kafkaspring;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "demo-topic", groupId = "group2")
    public void consume(String message) {
        System.out.println("Received message: " + message);
    }
}


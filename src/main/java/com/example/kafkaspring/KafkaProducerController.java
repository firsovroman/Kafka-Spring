package com.example.kafkaspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaProducerController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaProducerController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // http://localhost:8080/send
    @GetMapping("/send")
    public ResponseEntity<String> sendMessageToKafkaTopic() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            kafkaTemplate.send("demo-topic", String.valueOf(i % 3), "" + i);
            Thread.sleep(1000);
        }
        return new ResponseEntity<>("100 messages sent", HttpStatus.OK);
    }
}


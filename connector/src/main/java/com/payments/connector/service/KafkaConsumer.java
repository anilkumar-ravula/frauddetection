package com.payments.connector.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "cdc-events", groupId = "cdc-group")
    public void listen(ConsumerRecord<String, String> record) {
        System.out.println("Received from Kafka: " + record.value());
    }
}

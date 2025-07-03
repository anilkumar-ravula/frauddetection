package com.payments.connector.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic createTopic() {
        System.out.printf("new topic============");
        return new NewTopic("cdc-events", 1, (short) 1); // 3 partitions, 1 replication factor
    }
}

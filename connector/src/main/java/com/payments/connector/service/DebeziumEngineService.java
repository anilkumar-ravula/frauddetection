package com.payments.connector.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.config.Configuration;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@DependsOn("kafkaConfig")

public class DebeziumEngineService {

    private ExecutorService executor;
    private DebeziumEngine<ChangeEvent<String, String>> engine;
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private KafkaProducerService kafkaProducerService;
   // @Autowired
   // private KafkaAdminClient kafkaAdminClient;

    @PostConstruct
    public void start() {
        executor = Executors.newSingleThreadExecutor();
       // kafkaAdminClient.createTopics(List.of(new NewTopic("cdc-hello",1,(short)1)));
        Configuration config = Configuration.create()
                .with("name", "postgres-cdc-engine")
                .with("connector.class", "io.debezium.connector.postgresql.PostgresConnector")
               //.with("plugin.name", "wal2json")
                .with("plugin.name", "pgoutput")
                .with("slot.name", "debezium_slot")
                .with("publication.name", "my_publication")
                .with("database.hostname", "localhost")
                .with("database.port", "5432")
                .with("database.user", "postgres")
                .with("database.password", "postgres")
                .with("database.dbname", "payments")
                .with("database.server.name", "cdc-server")
                .with("table.include.list", "public.transactionsv1")
                .with("database.include.schema.changes", "false")
                .with("topic.prefix", "cdc")
                .with("offset.storage.file.filename", "C:\\Users\\Anil.Ravula\\de\\DE_TECH_EXPO_VeriFlux\\temp\\offset.data")

                .build();

        engine = DebeziumEngine.create(Json.class)
                .using(config.asProperties())
                .notifying(record -> {
                    System.out.println("Change event: " + record.value());
                    try {
                        kafkaProducerService.send(objectMapper.writeValueAsString(
                                ((Map)objectMapper.readValue(record.value(),
                                        new TypeReference<Map<String, Object>>() {})
                                        .get("payload"))
                                        .get("after")));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .build();

        executor.execute(engine);
    }

    @PreDestroy
    public void stop() {
        try {
            if (engine != null) engine.close();
            if (executor != null) executor.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package com.payments.connector;

import com.payments.connector.config.KafkaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(KafkaConfig.class)
public class ConnectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectorApplication.class, args);
	}

}

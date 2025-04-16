package org.example.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.kafka.producer")
public class KafkaProducerProperties {
    private String clientId;
    private String keySerializer;
    private String valueSerializer;
    private String starshipValueSerializer;
}
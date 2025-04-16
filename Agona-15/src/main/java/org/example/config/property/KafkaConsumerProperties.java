package org.example.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.kafka.consumer")
public class KafkaConsumerProperties {
    private String groupId;
    private String keyDeserializer;
    private String valueDeserializer;
    private String starshipValueDeserializer;
    private String starshipTrustedPackages;
    private String starshipDefaultType;
    private boolean enableAutoCommit;
    private boolean starshipEnableAutoCommit;
}
package com.technokratos.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.kafka.consumer")
public class KafkaConsumerProperties {
    private String groupId;
    private String keyDeserializer;
    private String valueDeserializer;
    private String trustedPackages;
    private String typeMappings;
    private boolean enableAutoCommit;
}
package com.technokratos.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.kafka.consumer")
public class KafkaConsumerProperties {
    private String groupId;
    private String keyDeserializer;
    private String paymentFailedEventValueDeserializer;
    private String inventoryReservedEventValueDeserializer;
    private String inventoryFailedEventValueDeserializer;
    private String trustedPackages;
    private String paymentFailedEventDefaultType;
    private String inventoryReservedEventDefaultType;
    private String inventoryFailedEventDefaultType;
    private boolean enableAutoCommit;
}
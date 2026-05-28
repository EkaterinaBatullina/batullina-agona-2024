package org.example.listener;

import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.dto.StarshipDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Getter
public class KafkaListeners {
    private StarshipDto lastReceivedMessageFromDLQ;
    private String lastReceivedStringMessage;
    private StarshipDto lastReceivedStarshipDto;

    @KafkaListener(topics = "string-events", containerFactory = "stringKafkaListenerContainerFactory")
    public void listenString(ConsumerRecord<Long, String> record) {
        this.lastReceivedStringMessage = record.value();
    }

    @RetryableTopic(attempts = "2",kafkaTemplate = "starshipDtoKafkaTemplate",
            backoff = @Backoff(delay = 1000, multiplier = 2, maxDelay = 5000))
    @KafkaListener(topics = "starship-events", containerFactory = "starshipDtoKafkaListenerContainerFactory")
    public void listenStarship(@Payload StarshipDto starshipDto) {
        this.lastReceivedStarshipDto = starshipDto;
        Optional.ofNullable(starshipDto.getType())
                .orElseThrow(() -> new NullPointerException("Type is null in StarshipDto"));
    }

    @KafkaListener(topics = "starship-events-dlt", groupId = "dlq-group",
            containerFactory = "starshipDtoKafkaListenerContainerFactory")
    public void listenToDLQ(@Payload StarshipDto starshipDto) {
        this.lastReceivedMessageFromDLQ  = starshipDto;
    }
}
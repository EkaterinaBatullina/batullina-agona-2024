package org.example.producer;

import lombok.RequiredArgsConstructor;
import org.example.dto.StarshipDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
    private static final String STRING_TOPIC = "string-events";
    private static final String STARSHIP_TOPIC = "starship-events";
    private final KafkaTemplate<Long, String> stringKafkaTemplate;
    private final KafkaTemplate<Long, StarshipDto> starshipKafkaTemplate;

    public void sendStringMessage(String message) {
        stringKafkaTemplate.send(STRING_TOPIC, message);
    }

    public void sendStarshipMessage(StarshipDto starshipDto) {
        starshipKafkaTemplate.send(STARSHIP_TOPIC, starshipDto);
    }
}
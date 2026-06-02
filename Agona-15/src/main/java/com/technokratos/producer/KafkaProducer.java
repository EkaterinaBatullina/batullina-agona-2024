package com.technokratos.producer;

import com.technokratos.event.StarshipEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<Long, Object> kafkaTemplate;

    /*
     * Универсальный producer для публикации событий.
     *
     * Тип события передается через Kafka-заголовок "__TypeId__",
     * который используется consumer для сопоставления сообщения
     * с конкретным Java-классом через TYPE_MAPPINGS.
     */
    public void sendEvent(String topic, String type, Object payload) {
        ProducerRecord<Long, Object> record = new ProducerRecord<>(topic, payload);

        record.headers().add(
                "__TypeId__",
                type.getBytes(StandardCharsets.UTF_8)
        );

        kafkaTemplate.send(record);
    }

    public void sendStringMessage(String message) {
        sendEvent("string-events", "string", message);
    }

    public void sendStarshipEvent(StarshipEvent event) {
        sendEvent("starship-events", "starship", event);
    }
}
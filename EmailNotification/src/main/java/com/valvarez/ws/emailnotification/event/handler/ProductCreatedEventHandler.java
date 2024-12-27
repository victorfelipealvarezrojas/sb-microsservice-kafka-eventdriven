package com.valvarez.ws.emailnotification.event.handler;

import com.valvarez.ws.core.ProductCreatedEvent;
import com.valvarez.ws.emailnotification.error.NotRetryAbleException;
import com.valvarez.ws.emailnotification.io.ProcessEventRepository;
import com.valvarez.ws.emailnotification.io.ProcessedEventEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Component
@KafkaListener(topics = "products-creates-events-topic") // puedo agregar el comsumer group id aqui groupId = "email-notification-group"
public class ProductCreatedEventHandler {

    private final Logger LOGGER = Logger.getLogger(ProductCreatedEventHandler.class.getName());
    private final ProcessEventRepository processEventRepository;

    public ProductCreatedEventHandler(ProcessEventRepository processEventRepository) {
        this.processEventRepository = processEventRepository;
    }

    @Transactional
    @KafkaHandler
    public void handle(
            @Payload ProductCreatedEvent productCreatedEvent,
            @Header(value = "message_id", required = false) String messageId,
            @Header(KafkaHeaders.RECEIVED_KEY) String messageKey
    ) {

        ProcessedEventEntity processedEvent = processEventRepository.findByMessageId(messageId);
        if (processedEvent != null) {
            LOGGER.info("=== Evento ya procesado, recepcion duplicada ===");
            return;
        }

        LOGGER.info("=== Iniciando procesamiento del mensaje ===");
        LOGGER.info("Product created event received: " + productCreatedEvent);
        LOGGER.info("Event details - ID: " + productCreatedEvent.getProductId());

        try {
            processEventRepository.save(new ProcessedEventEntity(messageId, productCreatedEvent.getProductId()));
        } catch (DataIntegrityViolationException die) {
            throw new NotRetryAbleException(die);
        }

    }
}
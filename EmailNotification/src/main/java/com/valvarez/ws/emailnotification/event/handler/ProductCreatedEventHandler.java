package com.valvarez.ws.emailnotification.event.handler;

import com.valvarez.ws.core.ProductCreatedEvent;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@KafkaListener(topics = "products-creates-events-topic")
public class ProductCreatedEventHandler {

    public ProductCreatedEventHandler() {
        LOGGER.info("=== ProductCreatedEventHandler initialized ===");
    }

    private final Logger LOGGER = Logger.getLogger(ProductCreatedEventHandler.class.getName());

    @KafkaHandler
    public void handle(ProductCreatedEvent productCreatedEvent) {
        LOGGER.info("=== Iniciando procesamiento del mensaje ===");
        LOGGER.info("Product created event received: " + productCreatedEvent);
        LOGGER.info("Event details - ID: " + productCreatedEvent.getProductId());
    }
}

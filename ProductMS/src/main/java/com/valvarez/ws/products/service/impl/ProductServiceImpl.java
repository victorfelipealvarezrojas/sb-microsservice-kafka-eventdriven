package com.valvarez.ws.products.service.impl;

import com.valvarez.ws.core.ProductCreatedEvent;
import com.valvarez.ws.products.dto.CreateProductRestModel;
import com.valvarez.ws.products.service.ProductService;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.logging.Logger;

import static com.valvarez.ws.products.config.KafkaConfig.PRODUCTS_CREATES_EVENTS_TOPIC;

@Service
public class ProductServiceImpl implements ProductService {

    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(CreateProductRestModel product) throws Exception {
        String productId = UUID.randomUUID().toString();

        // TODO: persist product in database

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(
                productId,
                product.getTitle(),
                product.getPrice(),
                product.getCuantity()
        );

        SendResult<String, ProductCreatedEvent> result =
                kafkaTemplate.send(PRODUCTS_CREATES_EVENTS_TOPIC, productId, productCreatedEvent).get(); //is sinchronous

//        CompletableFuture<SendResult<String, ProductCreatedEvent>> future =
//                kafkaTemplate.send(PRODUCTS_CREATES_EVENTS_TOPIC, productId, productCreatedEvent);
//        future.whenComplete((result, ex) -> {
//            if (ex != null) {
//                logger.severe("Error sending product created event: " + ex.getMessage());
//            } else {
//                logger.info("Product created event sent: " + result.getProducerRecord().value());
//            }
//        });
//        future.join(); // wait for the future to complete

        return productId;
    }
}
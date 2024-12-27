package com.valvarez.ws.products.service.impl;

import com.valvarez.ws.core.ProductCreatedEvent;
import com.valvarez.ws.products.dto.CreateProductRestModel;
import com.valvarez.ws.products.service.ProductService;

import org.apache.kafka.clients.producer.ProducerRecord;
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

        // TODO: send product created event to kafka
        ProducerRecord<String, ProductCreatedEvent> record = new ProducerRecord<>(
                PRODUCTS_CREATES_EVENTS_TOPIC,
                productId,
                productCreatedEvent
        );
        record.headers().add("message_id", UUID.randomUUID().toString().getBytes());


        SendResult<String, ProductCreatedEvent> result = kafkaTemplate.send(record).get(); //is sinchronous

        return productId;
    }
}
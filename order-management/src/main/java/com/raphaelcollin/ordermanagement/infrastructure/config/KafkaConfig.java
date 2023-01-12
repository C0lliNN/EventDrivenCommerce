package com.raphaelcollin.ordermanagement.infrastructure.config;

import com.raphaelcollin.ordermanagement.kafka.Item;
import com.raphaelcollin.ordermanagement.kafka.Order;
import org.apache.avro.generic.GenericRecord;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

@Configuration
public class KafkaConfig {

    @Bean
    public ConsumerFactory<String, Order> orderConsumerFactory(KafkaProperties kafkaProperties) {
        return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Order>> orderKafkaListenerContainerFactory(KafkaProperties kafkaProperties) {
        ConcurrentKafkaListenerContainerFactory<String, Order> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderConsumerFactory(kafkaProperties));
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Item> itemConsumerFactory(KafkaProperties kafkaProperties) {
        return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Item>> productKafkaListenerContainerFactory(KafkaProperties kafkaProperties) {
        ConcurrentKafkaListenerContainerFactory<String, Item> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(itemConsumerFactory(kafkaProperties));
        return factory;
    }

    @Bean
    public ConsumerFactory<GenericRecord, GenericRecord> shippingConsumerFactory(KafkaProperties kafkaProperties) {
        return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<GenericRecord, GenericRecord>> shippingKafkaListenerContainerFactory(KafkaProperties kafkaProperties) {
        ConcurrentKafkaListenerContainerFactory<GenericRecord, GenericRecord> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(shippingConsumerFactory(kafkaProperties));
        return factory;
    }
}

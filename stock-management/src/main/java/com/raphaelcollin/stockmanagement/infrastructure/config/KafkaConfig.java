package com.raphaelcollin.stockmanagement.infrastructure.config;

import com.raphaelcollin.ordermanagement.kafka.Order;
import com.raphaelcollin.stockmanagement.kafka.StockChange;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroDeserializer;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

import java.util.Map;

import static io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaConfig {

    @Value(value = "${kafka.schema.registry.url}")
    private String registryUrl;

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    KafkaStreamsConfiguration kStreamsConfig(KafkaProperties properties) {
        return new KafkaStreamsConfiguration(properties.buildStreamsProperties());
    }

    @Bean
    Serde<Order> orderSerde() {
        final Serde<Order> orderSerde = Serdes.serdeFrom(new SpecificAvroSerializer<>(), new SpecificAvroDeserializer<>());
        orderSerde.configure(Map.of(SCHEMA_REGISTRY_URL_CONFIG, registryUrl), true);
        return orderSerde;
    }

    @Bean
    Serde<StockChange> stockChangeSerde() {
        final Serde<StockChange> stockChangeSerde = Serdes.serdeFrom(new SpecificAvroSerializer<>(), new SpecificAvroDeserializer<>());
        stockChangeSerde.configure(Map.of(SCHEMA_REGISTRY_URL_CONFIG, registryUrl), true);
        return stockChangeSerde;
    }
}
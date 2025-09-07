package com.example.kafkaspring;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableKafka
public class KafkaConfig {

    // Настройка продюсера Kafka
    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {

        ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(producerConfigs());
        producerFactory.addListener(new ProducerFactory.Listener<String, String>() {
            @Override
            public void producerAdded(String id, Producer<String, String> producer) {
                log.info("Добавлен новый Producer with id: " + id);
                ProducerFactory.Listener.super.producerAdded(id, producer);
            }

            @Override
            public void producerRemoved(String id, Producer<String, String> producer) {
                ProducerFactory.Listener.super.producerRemoved(id, producer);
            }
        });

        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    // Настройка консюмера Kafka
    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group2");
        return props;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        ConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerConfigs());
        consumerFactory.addListener(new ConsumerFactory.Listener<String, String>() {
            @Override
            public void consumerAdded(String id, Consumer<String, String> consumer) {
                log.info("Добавлен новы Consumer with id: " + id);
                ConsumerFactory.Listener.super.consumerAdded(id, consumer);
            }

            @Override
            public void consumerRemoved(String id, Consumer<String, String> consumer) {
                ConsumerFactory.Listener.super.consumerRemoved(id, consumer);
            }
        });

        return consumerFactory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}

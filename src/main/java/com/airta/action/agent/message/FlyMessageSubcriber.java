package com.airta.action.agent.message;

import com.google.common.collect.ImmutableMap;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;

import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;

public class FlyMessageSubcriber {

    public static void main(String[] args) {

        Map<String, Object> consumerConfig = ImmutableMap.of(
                BOOTSTRAP_SERVERS_CONFIG, "http://40.117.113.67:9092",
                GROUP_ID_CONFIG, "sample"
        );

        DefaultKafkaConsumerFactory<String, String> kafkaConsumerFactory =
                new DefaultKafkaConsumerFactory<>(
                        consumerConfig,
                        new StringDeserializer(),
                        new StringDeserializer());

        ContainerProperties containerProperties = new ContainerProperties("sample_topic");
        containerProperties.setMessageListener((MessageListener<String, String>) record -> {
            //do something with received record
            System.out.println("## On Sample Message listeners ..");
        });

        ConcurrentMessageListenerContainer container =
                new ConcurrentMessageListenerContainer<>(
                        kafkaConsumerFactory,
                        containerProperties);

        container.start();
    }
}

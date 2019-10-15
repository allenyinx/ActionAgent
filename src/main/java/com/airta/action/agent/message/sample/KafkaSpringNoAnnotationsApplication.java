package com.airta.action.agent.message.sample;

import org.springframework.boot.SpringApplication;

import java.util.Set;

//@SpringBootApplication
public class KafkaSpringNoAnnotationsApplication {

    private final MyKafkaProducer producer;
    private final MyKafkaConsumer consumer;

    public static void main(String[] args) {
        SpringApplication.run(KafkaSpringNoAnnotationsApplication.class, args);
    }

    public KafkaSpringNoAnnotationsApplication() {
        this("http://40.117.113.67:9092", "action1", "flow");
    }

    public KafkaSpringNoAnnotationsApplication(String brokerAddress, String consumerTopic, String producerTopic) {
        consumer = new MyKafkaConsumer(brokerAddress, consumerTopic);
        consumer.start();

        producer = new MyKafkaProducer(brokerAddress, producerTopic);
    }

    public void sendMessage(String message) {
        producer.send(message);
    }

    public Set<String> consumedMessages() {
        return consumer.consumedMessages;
    }
}

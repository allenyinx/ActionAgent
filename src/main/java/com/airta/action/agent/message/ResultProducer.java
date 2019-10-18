package com.airta.action.agent.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class ResultProducer {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public ResultProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public boolean sendMessage(String topic, String key, String value) {

        try {
            logger.info("## sending topic: {}, key: {}, value: {}", topic, key, value);
            ListenableFuture<SendResult<String, String>> sendResult = kafkaTemplate.send(topic, key, value);
            return sendResult.isDone();
        } catch (Exception e) {
            logger.error("failed to produce message with topic: {}, error info: {}", topic, e.getLocalizedMessage());
            return false;
        }
    }
}

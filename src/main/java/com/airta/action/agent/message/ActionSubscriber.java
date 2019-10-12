package com.airta.action.agent.message;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * @author allenyin
 */
public class ActionSubscriber {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(topics = {"flow"})
    public void listen(ConsumerRecord<?, ?> record) {
        logger.info("listen action topic: " + record.key());
        logger.info("listen action topic value: " + record.value().toString());

    }
}

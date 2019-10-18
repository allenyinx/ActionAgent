package com.airta.action.agent.message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * produce action execution result and update sitemap message.
 *
 * @author allenyin
 */
@Component
public class ActionResultProducer extends ResultProducer {

    @Value("${kafka.producer.topic}")
    private String reportTopic;

    public ActionResultProducer(KafkaTemplate<String, String> kafkaTemplate) {
        super(kafkaTemplate);
    }

    public boolean sendReportMessage(String key, String value) {

        return sendMessage(reportTopic, key, value);
    }
}

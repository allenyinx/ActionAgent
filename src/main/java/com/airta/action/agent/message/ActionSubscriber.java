package com.airta.action.agent.message;

import com.airta.action.agent.entity.DriverConfig;
import com.airta.action.agent.action.ActionExecutor;
import com.airta.action.agent.handler.InputInvalidHandler;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

/**
 * @author allenyin
 */
@Component
public class ActionSubscriber {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    ServletContext servletContext;

    @Autowired
    private ActionExecutor actionExecutor;

    @Autowired
    private InputInvalidHandler inputInvalidHandler;

    @Value("${agent.init}")
    private boolean initAgentWhenStartup;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(topics = {"action"})
    public void listen(ConsumerRecord<?, ?> record) {

        String messageKey = record.key() == null ? "" : record.key().toString();
        String messageValue = record.value() == null ? "" : record.value().toString();
        logger.info("listen action key: " + messageKey);
        logger.info("listen action topic value: " + messageValue);

        WebDriver driver = null;
        if (initAgentWhenStartup) {
            driver = restoreWebDriverSession();
        } else {
            logger.warn("agent not initiated yet, try do first init.");
        }

        if (driver == null || record.value() == null) {
            inputInvalidHandler.run(record.key(), record.value());
        } else {
            actionExecutor.run(messageKey, messageValue, driver);
        }
    }

    private WebDriver restoreWebDriverSession() {

        Object storedSessionObject = servletContext.getAttribute(DriverConfig.WebDriverSessionKey);
        logger.info(storedSessionObject != null ? "session not null" : "session null");
        if (storedSessionObject == null) {
            return null;
        } else {
            return (WebDriver) storedSessionObject;
        }
    }
}

package com.airta.action.agent.message;

import com.airta.action.agent.entity.DriverConfig;
import com.airta.action.agent.action.ActionExecutor;
import com.airta.action.agent.handler.InputInvalidHandler;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(topics = {"action"})
    public void listen(ConsumerRecord<?, ?> record) {
        logger.info("listen action key: " + record.key());
        logger.info("listen action topic value: " + record.value().toString());

        WebDriver driver = restoreWebDriverSession();

        if(driver==null || record.value()==null) {
            InputInvalidHandler.getInstance().run(record.key(), record.value());
        } else {
            ActionExecutor.getInstance().run(record.key() == null ? "" : record.key().toString(),
                    record.value() == null ? "" : record.value().toString(),
                    driver);
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

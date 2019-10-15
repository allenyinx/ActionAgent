package com.airta.action.agent.message;

import com.airta.action.agent.entity.DriverConfig;
import com.airta.action.agent.webdriver.WebDriverState;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.openqa.selenium.By;
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

        Object storedSessionObject = servletContext.getAttribute(DriverConfig.WebDriverSessionKey);
        logger.info(storedSessionObject!=null?"session not null":"session null");

        if(record.value().toString().equals("print")) {
            logger.info(((WebDriver)storedSessionObject).getPageSource());
        } else if(record.value().toString().equals("login")) {
            ((WebDriver)storedSessionObject).findElement(By.name("登录")).click();
        } else if(record.value().toString().equals("back") ) {
            ((WebDriver)storedSessionObject).navigate().back();
        }
    }
}

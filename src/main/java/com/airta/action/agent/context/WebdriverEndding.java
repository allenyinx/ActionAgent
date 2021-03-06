package com.airta.action.agent.context;

import com.airta.action.agent.config.DriverConfig;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

@Component
public class WebdriverEndding implements ApplicationListener<ApplicationFailedEvent> {

    private static final Logger log = LoggerFactory.getLogger(WebdriverEndding.class);

    @Value("${agent.init}")
    private boolean initAgentWhenStartup;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    ServletContext servletContext;

    @Override
    public void onApplicationEvent(ApplicationFailedEvent applicationFailedEvent) {
        if(initAgentWhenStartup) {
            Object storedSessionObject = servletContext.getAttribute(DriverConfig.WebDriverSessionKey);
            if (storedSessionObject != null) {
                log.info("Ending webdriver session.");
                WebDriver driver = (WebDriver) storedSessionObject;
                driver.close();
            }
        }
    }
}

package com.airta.action.agent.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class WebdriverEndding implements ApplicationListener<ApplicationFailedEvent> {

    private static final Logger log = LoggerFactory.getLogger(WebdriverEndding.class);

    @Override
    public void onApplicationEvent(ApplicationFailedEvent applicationFailedEvent) {
        WebdriverInitializr.webDriver.close();
    }
}

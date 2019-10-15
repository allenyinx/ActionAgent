package com.airta.action.agent.context;

import com.airta.action.agent.entity.DriverConfig;
import com.airta.action.agent.utility.HtmlParser;
import com.airta.action.agent.utility.WebDriverStart;
import com.airta.action.agent.webdriver.WebDriverState;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

@Component
@SuppressWarnings("SpringJavaAutowiringInspection")
public class WebdriverInitializr implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(WebdriverInitializr.class);

    public static WebDriver webDriver = null;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    ServletContext servletContext;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.info("## WebdriverInitializr initialization logic ...");

        webDriver = WebDriverStart.browserEntry();

        String entryPageSource = "";
        try {
            entryPageSource = webDriver.getPageSource();

        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            try {
                entryPageSource = webDriver.getPageSource();

            } catch (Exception e1) {
                log.error(e1.getLocalizedMessage());
                entryPageSource = webDriver.getPageSource();
            }
        }

        log.info("## Initialized webDriver session {}", entryPageSource.length());
        log.info("## Fetch embedded children links {}", HtmlParser.parseChildLinks(entryPageSource).size());

        servletContext.setAttribute(DriverConfig.WebDriverSessionKey, webDriver);
        servletContext.setAttribute(DriverConfig.WebDriverSessionStatus, WebDriverState.INIT);
    }
}

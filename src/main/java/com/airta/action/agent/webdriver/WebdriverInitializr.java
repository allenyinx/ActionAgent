package com.airta.action.agent.webdriver;

import com.airta.action.agent.utility.HtmlParser;
import com.airta.action.agent.utility.WebDriverStart;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class WebdriverInitializr implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(WebdriverInitializr.class);

    public static WebDriver webDriver = null;

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

    }
}

package com.airta.action.agent.webdriver;

import com.airta.action.agent.webdriver.chrome.ChromeConfig;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class WebdriverInitializr implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(WebdriverInitializr.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.info("## WebdriverInitializr initialization logic ...");

        launchBrowser(ChromeConfig.initiate());
    }

    private static WebDriver launchBrowser(ChromeOptions chromeOptions) {

//        prepareEnvironment();
        WebDriver driver = new ChromeDriver(chromeOptions);
        navigateURL(driver, DriverConfig.ENTRY_PAGE);

        return driver;
    }

    private static void navigateURL(WebDriver webDriver, String url) {
        try {
            webDriver.get(url);

        } catch (TimeoutException e) {
            e.printStackTrace();
            webDriver.navigate().refresh();
        } catch (Exception e1) {
            e1.printStackTrace();
            try {
                webDriver.navigate().refresh();
            } catch (Exception e2) {
                e2.printStackTrace();
                return;
            }

        }
    }

    private static void prepareEnvironment() {

        System.setProperty("webdriver.chrome.driver", "driver/chromedriver_76");
    }
}

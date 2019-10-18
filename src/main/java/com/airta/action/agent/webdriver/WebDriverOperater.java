package com.airta.action.agent.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static java.util.concurrent.TimeUnit.SECONDS;

public class WebDriverOperater extends WebDriverWrapUp {

    public WebDriverOperater(WebDriver webDriver) {
        super(webDriver);
    }

    public boolean click(WebElement webElement) {

        if (isValidElement(webElement)) {
            try {
                webElement.click();
                return true;
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage());
                return false;
            }
        }
        return false;
    }

    public boolean submit(WebElement webElement) {

        if (isValidElement(webElement)) {
            try {
                webElement.submit();
                return true;
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage());
                return false;
            }
        }
        return false;
    }

    public boolean input(WebElement webElement, String value) {

        if (isValidElement(webElement)) {
            try {
                webElement.sendKeys(value);
                return true;
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage());
                return false;
            }
        }
        return false;
    }

    public boolean select(WebElement webElement, String value) {

        if (isValidElement(webElement)) {
            try {
                webElement.click();
                return true;
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage());
                return false;
            }
        }
        return false;
    }

    public void actionHalt() {

        actionHalt(DefaultActionHaltInterval);
    }

    public void actionHalt(int seconds) {

        try {
            Thread.sleep(seconds * 1_000L);
        } catch (InterruptedException e) {
            logger.error(e.getLocalizedMessage());
            Thread.currentThread().interrupt();
        }
    }

    public void waitForPageLoad() {

        webDriver.manage().timeouts().pageLoadTimeout(30, SECONDS);
    }

    private boolean isValidElement(WebElement webElement) {

        return webElement != null && webElement.isEnabled() && webElement.isDisplayed();
    }
}

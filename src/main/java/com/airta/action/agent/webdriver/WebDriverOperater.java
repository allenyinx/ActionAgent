package com.airta.action.agent.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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

    private boolean isValidElement(WebElement webElement) {

        return webElement != null && webElement.isEnabled() && webElement.isDisplayed();
    }
}

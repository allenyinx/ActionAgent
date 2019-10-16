package com.airta.action.agent.webdriver;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebDriverWrapUp {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected WebDriver webDriver;

    public WebDriverWrapUp(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
}

package com.airta.action.agent.action.atom;

import com.airta.action.agent.webdriver.WebDriverLocator;
import com.airta.action.agent.webdriver.WebDriverOperater;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractContextAction implements IAction {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected WebDriver webDriver;
    protected WebDriverOperater webDriverOperater;
    protected WebDriverLocator webDriverLocator;

    protected AbstractContextAction(WebDriver webDriver) {
        this.webDriver = webDriver;
        webDriverOperater = new WebDriverOperater(this.webDriver);
        webDriverLocator = new WebDriverLocator(this.webDriver);
    }

    public void interval() {
        //TODO
    }
}

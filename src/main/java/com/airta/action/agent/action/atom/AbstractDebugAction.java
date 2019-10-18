package com.airta.action.agent.action.atom;

import com.airta.action.agent.action.raw.RawAction;
import com.airta.action.agent.message.ResultProducer;
import com.airta.action.agent.webdriver.WebDriverCapturer;
import com.airta.action.agent.webdriver.WebDriverLocator;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDebugAction implements IAction {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected static final String debugRecordFolder = "/home/airbot/share";

    protected WebDriver webDriver;
    protected WebDriverLocator webDriverLocator;
    protected WebDriverCapturer webDriverCapturer;

    protected AbstractDebugAction(WebDriver webDriver) {
        this.webDriver = webDriver;
        webDriverLocator = new WebDriverLocator(this.webDriver);
        webDriverCapturer = new WebDriverCapturer(this.webDriver);
    }

    public void report(String key, RawAction rawAction, ResultProducer resultProducer) {

    }

    public void interval() {}
}

package com.airta.action.agent.action.atom;

import com.airta.action.agent.action.raw.RawAction;
import com.airta.action.agent.message.ActionResultProducer;
import com.airta.action.agent.message.ResultProducer;
import com.airta.action.agent.webdriver.WebDriverLocator;
import com.airta.action.agent.webdriver.WebDriverOperater;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDoAction implements IAction {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected WebDriver webDriver;
    protected WebDriverOperater webDriverOperater;
    protected WebDriverLocator webDriverLocator;

    protected AbstractDoAction(WebDriver webDriver) {
        this.webDriver = webDriver;

        webDriverOperater = new WebDriverOperater(this.webDriver);
        webDriverLocator = new WebDriverLocator(this.webDriver);
    }

    public void report(String key, RawAction rawAction, ResultProducer resultProducer) {

        logger.info("updating action report after execution.");


        ((ActionResultProducer)resultProducer).sendReportMessage(key, "");
    }

    public void interval() {
        webDriverOperater.actionHalt();
    }
}

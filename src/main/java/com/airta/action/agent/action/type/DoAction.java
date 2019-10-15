package com.airta.action.agent.action.type;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DoAction implements IAction {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected WebDriver webDriver;

    protected DoAction(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
}

package com.airta.action.agent.action.type.impl;

import com.airta.action.agent.action.type.DoAction;
import org.openqa.selenium.WebDriver;

public class PrintAction extends DoAction {

    public PrintAction(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void exec(String key, String value) {
        logger.info("## Print info");
        logger.info(webDriver.getPageSource());
    }
}

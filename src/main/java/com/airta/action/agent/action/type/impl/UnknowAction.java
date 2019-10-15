package com.airta.action.agent.action.type.impl;

import com.airta.action.agent.action.type.DoAction;
import org.openqa.selenium.WebDriver;

public class UnknowAction extends DoAction {

    public UnknowAction(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void exec(String key, String value) {
        logger.warn("## Unknown action");
    }
}

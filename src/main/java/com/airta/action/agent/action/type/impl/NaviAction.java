package com.airta.action.agent.action.type.impl;

import com.airta.action.agent.action.type.DoAction;
import org.openqa.selenium.WebDriver;

public class NaviAction extends DoAction {

    public NaviAction(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void exec(String key, String value) {

        logger.info("## Navi action");
        if(value.equals("back")) {
            webDriver.navigate().back();
        } else if(value.equals("forward")) {
            webDriver.navigate().forward();
        } else {
            webDriver.navigate().refresh();
        }
    }
}

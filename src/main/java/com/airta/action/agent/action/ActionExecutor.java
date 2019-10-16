package com.airta.action.agent.action;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionExecutor {

    private static ActionExecutor actionExecutor = null;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static ActionExecutor getInstance() {

        if(actionExecutor==null) {
            actionExecutor = new ActionExecutor();
        }
        return actionExecutor;
    }

    public void run(String key, String value, WebDriver webDriver) {

        ActionFactory.getInstance().getActionInstance(value, webDriver).exec(key, value);
    }
}

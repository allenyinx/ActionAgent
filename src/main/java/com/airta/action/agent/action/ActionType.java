package com.airta.action.agent.action;

import com.airta.action.agent.action.type.impl.*;
import com.airta.action.agent.action.type.IAction;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionType {

    private static ActionType actionType = null;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static ActionType getInstance() {

        if(actionType ==null) {
            actionType = new ActionType();
        }
        return actionType;
    }

    public IAction getActionInstance(String value, WebDriver webDriver) {

        if(value.equals("print") || value.equals("source")) {
            return new PrintAction(webDriver);
        } else if(value.equals("login") || value.equals("logout") || value.equals("click")) {
            return new ClickAction(webDriver);
        } else if(value.equals("back") || value.equals("forward") || value.equals("refresh") ) {
            return new NaviAction(webDriver);
        } else if(value.equals("screenshot") || value.equals("ss") || value.equals("screen") ) {
            return new ScreenshotAction(webDriver);
        } else {
            return new UnknowAction(webDriver);
        }
    }
}

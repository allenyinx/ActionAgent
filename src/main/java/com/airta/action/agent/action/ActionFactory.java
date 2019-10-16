package com.airta.action.agent.action;

import com.airta.action.agent.action.atom.impl.context.ClearAction;
import com.airta.action.agent.action.atom.impl.context.GotoAction;
import com.airta.action.agent.action.atom.impl.context.StartFromAction;
import com.airta.action.agent.action.atom.impl.debug.JsConsoleLogAction;
import com.airta.action.agent.action.atom.impl.debug.PrintAction;
import com.airta.action.agent.action.atom.impl.debug.ScreenshotAction;
import com.airta.action.agent.action.atom.impl.debug.UnknownAction;
import com.airta.action.agent.action.atom.impl.simple.*;
import com.airta.action.agent.action.raw.RawAction;
import com.airta.action.agent.action.atom.IAction;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Action factory to determine the actual action parser and operator.
 */
public class ActionFactory {

    private static ActionFactory actionFactory = null;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static ActionFactory getInstance() {

        if (actionFactory == null) {
            actionFactory = new ActionFactory();
        }
        return actionFactory;
    }

    public IAction getActionInstance(RawAction rawAction, WebDriver webDriver) {

        switch (rawAction.getAction()) {
            case CLICK:
                return new ClickAction(webDriver);
            case INPUT:
            case SETVALUE:
                return new InputAction(webDriver);
            case SUBMIT:
                return new SubmitAction(webDriver);
            case SELECT:
                return new SelectAction(webDriver);
            case NAVI_BACK:
            case NAVI_FORWARD:
            case NAVI_REFRESH:
                return new NaviAction(webDriver);
            case SCREENSHOT:
                return new ScreenshotAction(webDriver);
            case PRINTSOURCE:
                return new PrintAction(webDriver);
            case JSLOGCAPTURE:
                return new JsConsoleLogAction(webDriver);
            case GOTOPAGE:
                return new GotoAction(webDriver);
            case STARTFROM:
                return new StartFromAction(webDriver);
            case TEARDOWN:
            case CLEAR:
                return new ClearAction(webDriver);
            default:
                return new UnknownAction(webDriver);
        }
    }
}

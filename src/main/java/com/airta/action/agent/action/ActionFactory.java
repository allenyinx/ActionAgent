package com.airta.action.agent.action;

import com.airta.action.agent.action.atom.impl.context.ClearAction;
import com.airta.action.agent.action.atom.impl.context.GotoAction;
import com.airta.action.agent.action.atom.impl.context.StartFromAction;
import com.airta.action.agent.action.atom.impl.debug.*;
import com.airta.action.agent.action.atom.impl.simple.*;
import com.airta.action.agent.action.raw.RawAction;
import com.airta.action.agent.action.atom.IAction;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Action factory to determine the actual action parser and operator.
 */
@Component
public class ActionFactory {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

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
            case JSPRINTSOURCE:
                return new ExecJsPrintAction(webDriver);
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

package com.airta.action.agent.action.atom.impl.debug;

import com.airta.action.agent.action.atom.AbstractDebugAction;
import com.airta.action.agent.action.raw.RawAction;
import org.openqa.selenium.WebDriver;

public class UnknownAction extends AbstractDebugAction {

    public UnknownAction(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void exec(String key, RawAction rawAction) {
        logger.warn("## Unknown action");
    }
}

package com.airta.action.agent.action.atom.impl.context;

import com.airta.action.agent.action.atom.AbstractContextAction;
import com.airta.action.agent.action.raw.RawAction;
import org.openqa.selenium.WebDriver;

public class StartFromAction extends AbstractContextAction {

    public StartFromAction(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void exec(String key, RawAction rawAction) {
        logger.info("start from expected context action.");
    }
}

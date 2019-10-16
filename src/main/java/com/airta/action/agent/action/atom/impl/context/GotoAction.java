package com.airta.action.agent.action.atom.impl.context;

import com.airta.action.agent.action.atom.AbstractContextAction;
import com.airta.action.agent.action.raw.RawAction;
import org.openqa.selenium.WebDriver;

public class GotoAction extends AbstractContextAction {

    public GotoAction(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void exec(String key, RawAction rawAction) {
        logger.info("goto expected context action.");
    }
}

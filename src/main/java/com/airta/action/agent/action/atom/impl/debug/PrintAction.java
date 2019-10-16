package com.airta.action.agent.action.atom.impl.debug;

import com.airta.action.agent.action.atom.AbstractDebugAction;
import com.airta.action.agent.action.raw.RawAction;
import org.openqa.selenium.WebDriver;

public class PrintAction extends AbstractDebugAction {

    public PrintAction(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void exec(String key, RawAction rawAction) {
        logger.info("## Print info");
        logger.info(webDriver.getPageSource());
    }
}

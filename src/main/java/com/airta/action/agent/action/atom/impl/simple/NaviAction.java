package com.airta.action.agent.action.atom.impl.simple;

import com.airta.action.agent.action.raw.RawAction;
import com.airta.action.agent.action.atom.AbstractDoAction;
import org.openqa.selenium.WebDriver;

public class NaviAction extends AbstractDoAction {

    public NaviAction(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void exec(String key, RawAction rawAction) {

        logger.info("## Navi action");
        switch (rawAction.getAction()) {
            case NAVI_BACK:
                webDriver.navigate().back();
            case NAVI_FORWARD:
                webDriver.navigate().forward();
            case NAVI_REFRESH:
                webDriver.navigate().refresh();
            default:
                logger.warn("Unknown navigation action.");
        }
    }
}

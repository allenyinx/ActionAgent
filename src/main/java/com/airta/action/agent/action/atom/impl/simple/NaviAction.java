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

        logger.info("## Navi action: {}", rawAction.getAction());
        switch (rawAction.getAction()) {
            case NAVI_BACK:
                webDriver.navigate().back();
                break;
            case NAVI_FORWARD:
                webDriver.navigate().forward();
                break;
            case NAVI_REFRESH:
                webDriver.navigate().refresh();
                break;
            default:
                logger.warn("Unknown navigation action.");
        }

        interval();
    }
}

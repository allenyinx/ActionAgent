package com.airta.action.agent.action.atom.impl.debug;

import com.airta.action.agent.action.atom.AbstractDebugAction;
import com.airta.action.agent.action.raw.RawAction;
import org.openqa.selenium.WebDriver;

/**
 * @author allenyin
 */
public class JsConsoleLogAction extends AbstractDebugAction {

    public JsConsoleLogAction(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void exec(String key, RawAction rawAction) {
        logger.info("## Print js error log from console info");
        logger.info(webDriverCapturer.displayJSErrosLog());
    }


}

package com.airta.action.agent.action.atom.impl.debug;

import com.airta.action.agent.action.atom.AbstractDebugAction;
import com.airta.action.agent.action.raw.RawAction;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class ExecJsPrintAction extends AbstractDebugAction {

    public ExecJsPrintAction(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void exec(String key, RawAction rawAction) {
        logger.info("## Print js executor info");

        String javascript = "return arguments[0].innerHTML";
        String pageSource = (String) ((JavascriptExecutor) webDriver).
                executeScript(javascript, webDriver.findElement(By.tagName("html")));
        pageSource = "<html>" + pageSource + "</html>";
        logger.info("## JS executed page source: {}", pageSource);
    }
}

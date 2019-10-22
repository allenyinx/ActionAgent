package com.airta.action.agent.action.atom.impl.simple;

import com.airta.action.agent.action.raw.RawAction;
import com.airta.action.agent.action.raw.fields.ElementLocation;
import com.airta.action.agent.action.raw.fields.RawActionData;
import com.airta.action.agent.action.atom.AbstractDoAction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ClickAction extends AbstractDoAction {

    public ClickAction(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void exec(String key, RawAction rawAction) {
        logger.info("## Click action");
        RawActionData rawActionData = rawAction.getData();

        ElementLocation elementPath = rawActionData.getElementPath();
        WebElement elementToBeClicked = webDriverLocator.findElement(elementPath);
        if(elementToBeClicked==null) {
            logger.warn("## Ignore current element since cannot be located.");
            return;
        }
        webDriverOperater.click(elementToBeClicked);

        interval();
    }
}

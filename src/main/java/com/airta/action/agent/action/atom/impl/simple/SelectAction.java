package com.airta.action.agent.action.atom.impl.simple;

import com.airta.action.agent.action.raw.RawAction;
import com.airta.action.agent.action.raw.fields.ElementLocation;
import com.airta.action.agent.action.raw.fields.RawActionContext;
import com.airta.action.agent.action.raw.fields.RawActionData;
import com.airta.action.agent.action.atom.AbstractDoAction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SelectAction extends AbstractDoAction {

    public SelectAction(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void exec(String key, RawAction rawAction) {
        logger.info("## Select action");
        RawActionContext rawActionContext = rawAction.getContext();
        RawActionData rawActionData = rawAction.getData();

        ElementLocation elementPath = rawActionData.getElementPath();
        WebElement elementToBeInput = webDriverLocator.findElement(elementPath);
        webDriverOperater.select(elementToBeInput, rawActionData.getActionData());

        interval();
    }
}

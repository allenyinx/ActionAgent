package com.airta.action.agent.action.atom.impl.simple;

import com.airta.action.agent.action.raw.RawAction;
import com.airta.action.agent.action.raw.fields.ElementLocation;
import com.airta.action.agent.action.raw.fields.RawActionContext;
import com.airta.action.agent.action.raw.fields.RawActionData;
import com.airta.action.agent.action.atom.AbstractDoAction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InputAction extends AbstractDoAction {

    public InputAction(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void exec(String key, RawAction rawAction) {
        logger.info("## Input action within: {}:{}", rawAction.getData().getElementPath().getType(), rawAction.getData().getElementPath().getValue());
        RawActionContext rawActionContext = rawAction.getContext();
        RawActionData rawActionData = rawAction.getData();

        ElementLocation elementPath = rawActionData.getElementPath();
        WebElement elementToBeInput = webDriverLocator.findElement(elementPath);
        webDriverOperater.input(elementToBeInput, rawActionData.getActionData());

        interval();
    }
}

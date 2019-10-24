package com.airta.action.agent.utility;

import com.airta.action.agent.action.raw.fields.ElementLocation;
import com.airta.action.agent.entity.html.Element;
import com.airta.action.agent.entity.html.ElementType;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebDriverMagical {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private WebDriver webDriver;

    public WebDriverMagical(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public ElementLocation buildElementLocator(Element element, ElementType elementType) {

        logger.info("## Construct element: {} locator from WebDriver and current context ..", element.getElementId());
        ElementLocation elementLocation = new ElementLocation();



        return elementLocation;
    }

}

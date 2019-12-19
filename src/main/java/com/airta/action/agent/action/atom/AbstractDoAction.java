package com.airta.action.agent.action.atom;

import com.airta.action.agent.action.raw.RawAction;
import com.airta.action.agent.action.raw.fields.RawActionContext;
import com.airta.action.agent.entity.html.Element;
import com.airta.action.agent.message.ActionResultProducer;
import com.airta.action.agent.message.ResultProducer;
import com.airta.action.agent.utility.parser.JsonParser;
import com.airta.action.agent.webdriver.WebDriverCapturer;
import com.airta.action.agent.webdriver.WebDriverLocator;
import com.airta.action.agent.webdriver.WebDriverOperater;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author allenyin
 */
public abstract class AbstractDoAction implements IAction {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected WebDriver webDriver;
    protected WebDriverOperater webDriverOperater;
    protected WebDriverLocator webDriverLocator;
    protected WebDriverCapturer webDriverCapturer;

    protected JsonParser jsonParser;

    protected AbstractDoAction(WebDriver webDriver) {
        this.webDriver = webDriver;

        webDriverOperater = new WebDriverOperater(this.webDriver);
        webDriverLocator = new WebDriverLocator(this.webDriver);
        webDriverCapturer = new WebDriverCapturer(this.webDriver);

        jsonParser = new JsonParser();
    }

    @Override
    public void report(String key, RawAction rawAction, ResultProducer resultProducer) {

        logger.info("updating action report after execution.");
        RawActionContext rawActionContext = rawAction.getContext();
        if(rawActionContext!=null && rawActionContext.isShouldFetchChildren()) {
            Element pageElement = webDriverCapturer.readPageElementsRightNow(rawAction);
            logger.info("Page Element info: {}", pageElement.toString());

            String childElementContents = jsonParser.objectToJSONString(pageElement);
            ((ActionResultProducer)resultProducer).sendReportMessage(key, childElementContents);
        } else {
            logger.info("## skip fetch children context ..");
        }
    }

    @Override
    public void interval() {
        webDriverOperater.actionHalt();
    }
}

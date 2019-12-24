package com.airta.action.agent.action;

import com.airta.action.agent.action.atom.IAction;
import com.airta.action.agent.action.raw.RawAction;
import com.airta.action.agent.action.raw.fields.RawActionContext;
import com.airta.action.agent.config.DriverConfig;
import com.airta.action.agent.message.ActionResultProducer;
import com.airta.action.agent.utility.parser.JsonParser;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

/**
 * common entry for Action message handle.
 * @author allenyin
 */
@Component
public class ActionExecutor {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final JsonParser jsonParser;

    private final ActionFactory actionFactory;

    private final ActionResultProducer actionResultProducer;

    private final ServletContext servletContext;

    @Autowired
    public ActionExecutor(JsonParser jsonParser, ActionFactory actionFactory, ActionResultProducer actionResultProducer, ServletContext servletContext) {
        this.jsonParser = jsonParser;
        this.actionFactory = actionFactory;
        this.actionResultProducer = actionResultProducer;
        this.servletContext = servletContext;
    }

    public void run(String key, String value, WebDriver webDriver) {

        RawAction[] rawActions = resolveRawActionMessage(value);
        if (rawActions != null && rawActions.length>0) {
            runOrderedActions(key, rawActions, webDriver);
        } else {
            logger.warn("SKIP input Action message exec, since cannot be resolved.");
        }
    }

    public void runOrderedActions(String key, RawAction[] rawActions, WebDriver webDriver) {

        for(int index=0;index<rawActions.length;index++) {
            RawAction rawAction = rawActions[index];
            logger.info("## RawAction info: {}", rawAction);

            rawAction.setId(String.valueOf(index));
            if(index==rawActions.length-1) {
                setFurtherFetchingContext(rawAction);
            }

            IAction action = actionFactory.getActionInstance(rawAction, webDriver);
            action.exec(key, rawAction);
            action.report(key, rawAction, actionResultProducer);

            servletContext.setAttribute(DriverConfig.WebDriverPagePathID, String.valueOf(index));
        }
    }

    /**
     * resolve the raw action arrays from incoming raw message.
     * @param rawData
     * @return
     */
    private RawAction[] resolveRawActionMessage(String rawData) {

        if (jsonParser.isJSONArray(rawData)) {
            Object rawObject = jsonParser.resolveIncomingMessage(rawData, RawAction[].class);
            if (rawObject != null) {
                return (RawAction[]) rawObject;
            } else {
                logger.error("Cannot resolve raw data to expected RawAction");
            }
        } else if (jsonParser.isJSONValid(rawData)) {
            Object rawObject = jsonParser.resolveIncomingMessage(rawData, RawAction.class);
            if (rawObject != null) {
                return new RawAction[] { (RawAction) rawObject };
            } else {
                logger.error("Cannot resolve raw data to expected RawAction");
            }
        } else {
            logger.error("Input rawData is not standard JSON FORMAT!");
        }
        return new RawAction[0];
    }

    public boolean runToPage(String pagePath) {

        logger.info("## trying run to expected page ..");
        return true;
    }

    /**
     * enable the last rawAction to fetch children.
     * @param rawAction
     */
    private void setFurtherFetchingContext(RawAction rawAction) {

        RawActionContext rawActionContext = new RawActionContext();
        if(rawAction.getContext()==null) {
            rawAction.setContext(rawActionContext);
        }
        rawAction.getContext().setShouldFetchChildren(true);
    }
}

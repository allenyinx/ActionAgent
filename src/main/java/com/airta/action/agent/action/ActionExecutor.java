package com.airta.action.agent.action;

import com.airta.action.agent.action.atom.IAction;
import com.airta.action.agent.action.raw.RawAction;
import com.airta.action.agent.message.ActionResultProducer;
import com.airta.action.agent.utility.parser.JsonParser;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * common entry for Action message handle.
 */
@Component
public class ActionExecutor {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JsonParser jsonParser;

    @Autowired
    private ActionFactory actionFactory;

    @Autowired
    private ActionResultProducer actionResultProducer;

    public void run(String key, String value, WebDriver webDriver) {

        RawAction[] rawActions = resolveRawActionMessage(value);
        if (rawActions != null && rawActions.length>0) {
            runOrderedActions(key, rawActions, webDriver);
        } else {
            logger.warn("SKIP input Action message exec, since cannot be resolved.");
        }
    }

    private void runOrderedActions(String key, RawAction[] rawActions, WebDriver webDriver) {

        for(RawAction rawAction: rawActions) {
            logger.info("## RawAction info: {}", rawAction);

            IAction action = actionFactory.getActionInstance(rawAction, webDriver);
            action.exec(key, rawAction);
            action.report(key, rawAction, actionResultProducer);
        }
    }

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
}

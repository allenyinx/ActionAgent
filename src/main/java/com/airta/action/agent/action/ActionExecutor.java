package com.airta.action.agent.action;

import com.airta.action.agent.action.raw.RawAction;
import com.airta.action.agent.utility.parser.JsonParser;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * common entry for Action message handle.
 */
public class ActionExecutor {

    private static ActionExecutor actionExecutor = null;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static JsonParser jsonParser = null;

    public static ActionExecutor getInstance() {

        if (actionExecutor == null) {
            actionExecutor = new ActionExecutor();
            jsonParser = new JsonParser();
        }
        return actionExecutor;
    }

    public void run(String key, String value, WebDriver webDriver) {

        RawAction rawAction = resolveRawActionMessage(value);
        if (rawAction != null) {
            ActionFactory.getInstance().getActionInstance(rawAction, webDriver).exec(key, rawAction);
        } else {
            logger.warn("SKIP input Action message exec, since cannot be resolved.");
        }
    }

    private RawAction resolveRawActionMessage(String rawData) {

        if (jsonParser.isJSONValid(rawData)) {
            Object rawObject = jsonParser.resolveIncomingMessage(rawData, RawAction.class);
            if (rawObject != null) {
                return (RawAction) rawObject;
            } else {
                logger.error("Cannot resolve raw data to expected RawAction");
            }
        } else {
            logger.error("Input rawData is not standard JSON FORMAT!");
        }
        return null;
    }
}

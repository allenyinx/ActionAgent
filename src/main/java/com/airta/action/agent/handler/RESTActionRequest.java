package com.airta.action.agent.handler;

import com.airta.action.agent.action.ActionExecutor;
import com.airta.action.agent.action.raw.RawAction;
import com.airta.action.agent.action.raw.fields.RawActionContext;
import com.airta.action.agent.entity.AgentState;
import com.airta.action.agent.entity.DriverConfig;
import io.micrometer.core.instrument.util.StringUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

@Component
public class RESTActionRequest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    ServletContext servletContext;

    @Autowired
    private ActionExecutor actionExecutor;

    @Autowired
    private InputInvalidHandler inputInvalidHandler;

    @Value("${agent.init}")
    private boolean initAgentWhenStartup;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public boolean runRawActionOnAgent(RawAction rawAction) {

        WebDriver webDriver = restoreWebDriverSession();
        if (webDriver != null) {
            logger.info("## run action against the restored webdriver session ..");
            actionExecutor.runOrderedActions("raw", new RawAction[]{rawAction}, webDriver);
            return true;
        } else {
            logger.warn("## cannot recover the agent driver session ..");
            return false;
        }
    }

    public boolean recoverAgentContext(RawAction rawAction) {

        RawActionContext rawActionContext = rawAction.getContext();

        /**
         * run to the expected context: page
         */
        if (rawActionContext == null || StringUtils.isEmpty(rawActionContext.getPagePath())) {
            return false;
        }
        return actionExecutor.runToPage(rawActionContext.getPagePath());
    }

    public AgentState queryAgentState() {

        AgentState agentState = new AgentState();
        WebDriver webDriver = restoreWebDriverSession();

        if (webDriver != null) {
            agentState.setCurrentUrl(webDriver.getCurrentUrl());
            agentState.setCurrentTitle(webDriver.getTitle());
            agentState.setPageSource(webDriver.getPageSource());
            agentState.setScreenshot("");
            agentState.setJslog("");
            agentState.setState("active");
        } else {
            agentState.setState("inactive");
        }

        return agentState;
    }

    private WebDriver restoreWebDriverSession() {

        Object storedSessionObject = servletContext.getAttribute(DriverConfig.WebDriverSessionKey);
        logger.info(storedSessionObject != null ? "session not null" : "session null");
        if (storedSessionObject == null) {
            return null;
        } else {
            return (WebDriver) storedSessionObject;
        }
    }
}

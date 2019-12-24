package com.airta.action.agent.handler;

import com.airta.action.agent.action.ActionExecutor;
import com.airta.action.agent.action.raw.RawAction;
import com.airta.action.agent.action.raw.fields.RawActionContext;
import com.airta.action.agent.entity.AgentState;
import com.airta.action.agent.config.DriverConfig;
import com.airta.action.agent.entity.html.Element;
import com.airta.action.agent.utility.WebDriverStart;
import com.airta.action.agent.utility.parser.JsonParser;
import com.airta.action.agent.webdriver.WebDriverCapturer;
import com.airta.action.agent.webdriver.WebDriverState;
import io.micrometer.core.instrument.util.StringUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

/**
 * @author allenyin
 */
@Component
public class RestActionRequest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    ServletContext servletContext;

    @Autowired
    private ActionExecutor actionExecutor;

    @Autowired
    private InputInvalidHandler inputInvalidHandler;

    @Autowired
    private JsonParser jsonParser;

    @Value("${agent.init}")
    private boolean initAgentWhenStartup;

    private WebDriverCapturer webDriverCapturer = null;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public boolean initBrowserStartPage(String startUrl) {

        WebDriver webDriver = restoreWebDriverSession();
        if (webDriver != null) {
            logger.info("## webdriver session restored ..");
            webDriver.close();
        }

        webDriver = WebDriverStart.browserEntry(startUrl);

        servletContext.setAttribute(DriverConfig.WebDriverSessionKey, webDriver);
        servletContext.setAttribute(DriverConfig.WebDriverSessionStatus, WebDriverState.INIT);

        WebDriverStart.initPage(webDriver, startUrl);
        WebDriverStart.waitForPageLoad(webDriver);
        return true;
    }

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
            webDriverCapturer = new WebDriverCapturer(webDriver);

            agentState.setCurrentUrl(webDriver.getCurrentUrl());
            agentState.setCurrentTitle(webDriver.getTitle());
            agentState.setCurrentPagePathID(readCurrentServletContext());
            agentState.setPageSource(webDriverCapturer.readCurrentPageSource());
            agentState.setScreenshot("");
            agentState.setJsLog(webDriverCapturer.displayJSErrosLog());
            agentState.setState("active");
        } else {
            agentState.setState("inactive");
        }

        return agentState;
    }

    public String fetchImmediateChildPages(RawAction rawAction) {

        WebDriver webDriver = restoreWebDriverSession();

        if (webDriver != null) {
            webDriverCapturer = new WebDriverCapturer(webDriver);

            Element pageElement = webDriverCapturer.readPageElementsRightNow(rawAction);
            logger.info("Page Element info: {}", pageElement.toString());

            return jsonParser.objectToJSONString(pageElement);
        } else {
            return null;
        }
    }

    private WebDriver restoreWebDriverSession() {

        Object storedSessionObject = servletContext.getAttribute(DriverConfig.WebDriverSessionKey);
        logger.info(storedSessionObject != null ? "session not null" : "session null");
        if (storedSessionObject == null) {
            WebDriver webDriver = WebDriverStart.browserEntry(null);

            servletContext.setAttribute(DriverConfig.WebDriverSessionKey, webDriver);
            servletContext.setAttribute(DriverConfig.WebDriverSessionStatus, WebDriverState.INIT);
            return webDriver;
        } else {
            return (WebDriver) storedSessionObject;
        }
    }

    private String readCurrentServletContext() {

        Object pathIdObj = servletContext.getAttribute(DriverConfig.WebDriverPagePathID);
        if (pathIdObj != null) {
            return String.valueOf(pathIdObj);
        } else {
            return "";
        }
    }
}
